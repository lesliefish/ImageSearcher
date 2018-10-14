#include "MainWindow.h"
#include "ui_MainWindow.h"
#include <QFileDialog>

namespace imagesearcher
{
    MainWindow::MainWindow(QWidget *parent)
        : FramelessWidget(parent)
    {
        ui = new Ui::MainWindow();
        ui->setupUi(this);

        initUi();
        initConnection();
    }

    MainWindow::~MainWindow()
    {
        delete ui;
    }

    void MainWindow::initUi()
    {
        ui->tableWidget->setRowCount(10);
        ui->tableWidget->setColumnCount(6);
        ui->tableWidget->horizontalHeader()->hide();
        ui->tableWidget->verticalHeader()->hide();
        ui->tableWidget->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
        ui->tableWidget->setVerticalScrollBarPolicy(Qt::ScrollBarAlwaysOff);

        int itemHeight = this->width() / 6;
        for (int i = 0; i < ui->tableWidget->rowCount(); ++i)
        {
            ui->tableWidget->setRowHeight(i, itemHeight);
            if (i < ui->tableWidget->columnCount())
            {
                ui->tableWidget->setColumnWidth(i, itemHeight);
            }
        }


        // 加载皮肤样式qss
        QString qssPath{ ":/qss/ImageSearcher.qss" };
        QFile file(qssPath);
        if (file.open(QFile::ReadOnly))
        {
            setStyleSheet(file.readAll());
        }
    }

    /** @fn     imagesearcher::MainWindow::initConnection
     *  @brief  
     *  @return void 
     */
    void MainWindow::initConnection()
    {
        connect(ui->closeBtn, &QPushButton::clicked, [&] { close(); });
        connect(ui->minBtn, &QPushButton::clicked, [&] { showMinimized(); });
        connect(&m_socket, &QTcpSocket::readyRead, [&] {recieveFromSever(); });

        connect(ui->openFileBtn, &QPushButton::clicked, [&]
        {
            QString path = QFileDialog::getOpenFileName(this, tr("Open File"), "/", tr("Image Files (*.jpg *.png)"));
            if (path.isEmpty())
            {
                return;
            }

            QString labelImageSetQss = QString("QLabel{border-image : url(%1);}").arg(path);
            ui->selectedImageLabel->setStyleSheet(labelImageSetQss);
            ui->selectedImageLabel->clear();

            m_curImagePath = path;
        });

        // 检索
        connect(ui->searchBtn, &QPushButton::clicked, [&]
        {
            if (m_curImagePath.isEmpty())
            {
                return;
            }

            QString action = "SEARCH";
            QString depends = ui->searchTypeCombo->currentText().trimmed();
            sendRequest(action, m_curImagePath, depends);

            ui->tableWidget->clear();
        });
    }

    /** @fn     imagesearcher::MainWindow::sendRequest
     *  @brief  发送请求
     *  @param  const QString & action 动作
     *  @param  const QString & filePath 要比对的图像路径
     *  @param  const QString & depends 搜索/索引依据
     *  @return void
     */
    void MainWindow::sendRequest(const QString& action, const QString& filePath, const QString& depends)
    {
        m_socket.connectToHost("127.0.0.1", 12345);
        QString sendStr = action + "CSU" + filePath + "CSU" + depends + "CSU" + "\n";
        bool connected = m_socket.waitForConnected(5000); // 超时5秒

        if (!connected)
        {
            // 连接服务器失败
            return;
        }

        m_socket.write(sendStr.toStdString().c_str(), sendStr.size());
        m_socket.waitForBytesWritten();
    }

    /** @fn     imagesearcher::MainWindow::recieveFromSever
     *  @brief  处理服务器接收到的数据
     *  @return void
     */
    void MainWindow::recieveFromSever()
    {
        QString recievedString = m_socket.readAll();
        m_socket.disconnected();

        if (recievedString.trimmed().compare("Index over") == 0)
        {
            // 索引完毕消息

            return;
        }

        // 检索出的图像结果
        QStringList paths = recievedString.split("CSU");
        if (!paths.empty())
        {
            paths.removeLast();
            showResult(paths);
        }
    }

    /** @fn     imagesearcher::MainWindow::showResult
     *  @brief  genju jiansuojieguo xianshichulai
     *  @param  const QStringList paths
     *  @return void
     */
    void MainWindow::showResult(const QStringList paths)
    {
        //第k幅图像 
        int k = 0;
        for (int i = 0; i < ui->tableWidget->rowCount(); i++)
        {
            for (int j = 0; j < ui->tableWidget->columnCount(); j++)
            {
                QLabel *label = new QLabel();
                label->setScaledContents(true);
                QImage* img = new QImage();
                img->load(paths[k]);
                label->setPixmap(QPixmap::fromImage(*img));
                label->setToolTip(paths[k] + "");
                ui->tableWidget->setCellWidget(i, j, label);
                k++;
            }
        }
    }

}

