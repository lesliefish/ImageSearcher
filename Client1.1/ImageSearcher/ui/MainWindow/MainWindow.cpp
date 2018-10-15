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
        infoTextWidget = new InfoTextWidget;
        infoTextWidget->setOKBtnVisible(false);
        infoTextWidget->setCancelBtnText(tr("Close"));

        initCommboxDatas();

        ui->tableWidget->setRowCount(10);
        ui->tableWidget->setColumnCount(6);
        ui->tableWidget->horizontalHeader()->hide();
        ui->tableWidget->verticalHeader()->hide();
        ui->tableWidget->setHorizontalScrollBarPolicy(Qt::ScrollBarAlwaysOff);
        ui->tableWidget->setVerticalScrollBarPolicy(Qt::ScrollBarAlwaysOff);

        // 加载皮肤样式qss
        QString qssPath{ ":/qss/ImageSearcher.qss" };
        QFile file(qssPath);
        if (file.open(QFile::ReadOnly))
        {
            setStyleSheet(file.readAll());
        }
    }

    /** @fn     imagesearcher::MainWindow::initCommboxDatas
     *  @brief  初始化选择列表内容
     *  @return void 
     */
    void MainWindow::initCommboxDatas()
    {
        QStringList items
        { "AutoColorCorrelogram","BinaryPatternsPyramid", "CEDD","ColorLayout", 
            "EdgeHistogram","FCTH", "FuzzyColorHistogram","Gabor", "JCD",
            "JpegCoefficientHistogram","LocalBinaryPatterns","LuminanceLayout", "OpponentHistogram",
            "PHOG", "RotationInvariantLocalBinaryPatterns","ScalableColor", 
            "SimpleColorHistogram","Tamura", "ALL"
        };
        ui->indexComboBox->addItems(items);
        items.removeLast();
        ui->searchComboBox->addItems(items);
    }

    /** @fn     imagesearcher::MainWindow::initConnection
     *  @brief  
     *  @return void 
     */
    void MainWindow::initConnection()
    {
        connect(ui->closeBtn, &QPushButton::clicked, [&] { close(); });
        connect(ui->normalBtn, &QPushButton::clicked, [&] { isMaximized() ? showNormal() : showMaximized(); });
        connect(ui->minBtn, &QPushButton::clicked, [&] { showMinimized(); });
        connect(&m_socket, &QTcpSocket::readyRead, [&] {recieveFromSever(); });
        // 选择一张图片
        connect(ui->openFileBtn, &QPushButton::clicked, [&] { chooseFile(); });
        // 检索
        connect(ui->searchBtn, &QPushButton::clicked, [&] {search(); });
        // 创建索引
        connect(ui->createIndexBtn, &QPushButton::clicked, [&] {createIndex();});
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
        QString sendStr = action + "-CSU-" + filePath + "-CSU-" + depends + "-CSU-" + "\n";
        bool connected = m_socket.waitForConnected(5000); // 超时5秒

        if (!connected)
        {
            // 连接服务器失败
            infoTextWidget->setTipText(tr("Connect server failed."));
            infoTextWidget->show();
            return;
        }

        m_socket.write(sendStr.toUtf8().data(), sendStr.toUtf8().size());
    }

    /** @fn     imagesearcher::MainWindow::recieveFromSever
     *  @brief  处理服务器接收到的数据
     *  @return void
     */
    void MainWindow::recieveFromSever()
    {
        QString recievedString = m_socket.readAll();
        m_socket.disconnected();

        if (recievedString.trimmed().compare("Index over!") == 0)
        {
            // 索引完毕消息
            infoTextWidget->setTipText(tr("Index over!"));
            infoTextWidget->show();
            ui->createIndexBtn->setEnabled(true);
            ui->createIndexBtn->setText(tr("Create Index"));
            return;
        }

        // 检索出的图像结果
        QStringList paths = recievedString.split("-CSU-");
        if (!paths.empty())
        {
            paths.removeLast();
            showResult(paths);
        }
    }

    /** @fn     imagesearcher::MainWindow::search
     *  @brief  执行检索操作
     *  @return void 
     */
    void MainWindow::search()
    {
        if (m_curImagePath.isEmpty())
        {
            infoTextWidget->setTipText(tr("Please select image file first."));
            infoTextWidget->show();
            return;
        }

        QString action = "SEARCH";
        QString depends = ui->searchComboBox->currentText().trimmed();
        sendRequest(action, m_curImagePath, depends);
    }

    /** @fn     imagesearcher::MainWindow::createIndex
     *  @brief  创建索引文件
     *  @return void 
     */
    void MainWindow::createIndex()
    {
        QString action = "INDEX";
        QString depends = ui->indexComboBox->currentText().trimmed();
        sendRequest(action, "", depends);

        ui->createIndexBtn->setText(tr("Creating index..."));
        ui->createIndexBtn->setEnabled(false);
    }

    /** @fn     imagesearcher::MainWindow::showResult
     *  @brief  根据检索结果显示出来
     *  @param  const QStringList paths 文件路径
     *  @return void
     */
    void MainWindow::showResult(const QStringList paths)
    {
        ui->tableWidget->clear();
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
                if (k == paths.size()) 
                {
                    return;
                }
            }
        }
    }

    /** @fn     imagesearcher::MainWindow::chooseFile
     *  @brief  选择一个本地文件
     *  @return void 
     */
    void MainWindow::chooseFile()
    {
        QString openDir{ "" };
        if (!m_curImagePath.isEmpty())
        {
            int dotPos = m_curImagePath.lastIndexOf("/");
            openDir = m_curImagePath.left(dotPos + 1);
        }
        QString path = QFileDialog::getOpenFileName(this, tr("Open File"), openDir, tr("Image Files (*.jpg *.png)"));
        if (path.isEmpty())
        {
            return;
        }

        QString labelImageSetQss = QString("QLabel{border-image : url(%1);}").arg(path);
        ui->selectedImageLabel->setStyleSheet(labelImageSetQss);
        ui->selectedImageLabel->setToolTip(path);
        ui->selectedImageLabel->clear();

        m_curImagePath = path;
    }

    void MainWindow::resizeEvent(QResizeEvent * event)
    {
        if (ui != nullptr)
        {
            // 当窗口大小更改时  更改显示的图片大小
            int itemHeight = (this->width() - ui->operateWidget->width() - 6) / 6;
            for (int i = 0; i < ui->tableWidget->rowCount(); ++i)
            {
                ui->tableWidget->setRowHeight(i, itemHeight);
                if (i < ui->tableWidget->columnCount())
                {
                    ui->tableWidget->setColumnWidth(i, itemHeight);
                }
            }
        }

        return FramelessWidget::resizeEvent(event);
    }

}

