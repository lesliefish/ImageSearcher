#include <QFile>
#include <QDir>
#include <QFileDialog>
#include <QPalette>
#include <QDebug>
#include <QIcon>
#include "mainwindow.h"
#include "ui_mainwindow.h"


MainWindow::MainWindow(QWidget *parent) :
    FramelessWidget(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    m_pSocket = new QTcpSocket(this);

    InitUI();
    InitConnect();
}

MainWindow::~MainWindow()
{
    delete ui;
    m_pSocket->disconnected();
}

/**
 *  @brief  初始化界面
 *  @name
 *  @author lesliefish
 *  @param  none
 *  @return
 */
void MainWindow::InitUI()
{
    this->setWindowFlags(Qt::FramelessWindowHint);
    QIcon icon(":/new/Icon/images/main.png");
    this->setWindowIcon(icon);
    this->setWindowTitle(ui->m_appNameBtn->text());

    QString stylePath = QDir::currentPath() + "/res/main.qss";
    QFile fileQss(stylePath);
    if(!fileQss.open(QFile::ReadOnly))
        return;
    this->setStyleSheet(fileQss.readAll());

    ui->m_maxBtn->setVisible(false);
    ui->m_showTableWidget->setRowCount(10);
    ui->m_showTableWidget->setColumnCount(6);

    for(int i = 0; i < ui->m_showTableWidget->rowCount(); ++i)
    {
        ui->m_showTableWidget->setRowHeight(i, 130);
        if(i<ui->m_showTableWidget->columnCount())
            ui->m_showTableWidget->setColumnWidth(i, 130);
    }
}

/**
 *  @brief  初始化连接
 *  @name
 *  @author lesliefish
 *  @param  none
 *  @return
 */
void MainWindow::InitConnect()
{
    connect(ui->m_helpBtn, &QPushButton::clicked, [=](){m_aboutDlg.exec();});
    connect(ui->m_minBtn, &QPushButton::clicked, [=](){this->showMinimized();});
    connect(ui->m_maxBtn, &QPushButton::clicked, [=](){this->isMaximized() ? this->showNormal() : this->showMaximized();});
    connect(ui->m_closeBtn, &QPushButton::clicked,[=](){m_warningDlg.exec();});
    connect(&m_warningDlg, &WarningDlg::SigExit, [=](){this->close();});

    connect(ui->m_chooseImageBtn, &QPushButton::clicked, this, &MainWindow::OpenImageFile);
    connect(this, &MainWindow::SigOpenFileSuccess, this, &MainWindow::ShowChoosedImage);

    //检索
    connect(ui->m_startSearchBtn, &QPushButton::clicked,
            [=]()
    {
        if(m_openedFilePath.size()==0)
        {
            return;
        }
        ui->m_showTableWidget->clear();
        QString searchAction = "SEARCH";
        QString searchDepends = ui->m_searchTypeCombo->currentText().trimmed();
        SendRequest(searchAction, m_openedFilePath, searchDepends);
    }
    );

    //索引
    connect(ui->m_createIndexBtn, &QPushButton::clicked,
            [=]()
    {
        QString indexAction = "INDEX";
        QString indexDepends = ui->m_searchTypeCombo->currentText().trimmed();
        SendRequest(indexAction, m_openedFilePath, indexDepends);
        ui->m_createIndexBtn->setText(tr("索引中,请稍后..."));
        ui->m_createIndexBtn->setEnabled(false);
    }
    );

    connect(m_pSocket, &QAbstractSocket::readyRead, [=](){ReadMessage();});
}

/**
 *  @brief 打开一个本地图像文件
 *  @name
 *  @author lesliefish
 *  @param  none
 *  @return 成功返回true,失败返回false
 */
bool MainWindow::OpenImageFile()
{
    m_openedFilePath = QFileDialog::getOpenFileName(this,
                                                    tr("打开文件"),
                                                    "/",
                                                    tr("图像文件(*.jpg *.png)"));
    if(!m_openedFilePath.size())
        return false;
    qDebug() << m_openedFilePath;
    emit SigOpenFileSuccess(m_openedFilePath);
    return true;
}

/**
 *  @brief  向服务端发送索引或者检索请求
 *  @name
 *  @author lesliefish
 *  @param
 *  @return
 */
void MainWindow::SendRequest(QString& strAction, QString& filepath, QString& depends)
{
    m_pSocket->connectToHost("127.0.0.1", 12345);
    bool connected = m_pSocket->waitForConnected();// waitForConnected();
    QString sendStr = strAction + "CSU" + filepath + "CSU" + depends + "CSU" + "\n";

    if(connected)
    {
        m_pSocket->write(sendStr.toStdString().c_str(), sendStr.size());
        m_pSocket->waitForBytesWritten();
    }
}

/**
 *  @brief  接受服务端发来的数据
 *  @name
 *  @author lesliefish
 *  @param
 *  @return
 */
void MainWindow::ReadMessage()
{
    QString strImagesPath(m_pSocket->readAll());
    m_pSocket->disconnected();

    //索引结果处理
    if(strImagesPath.trimmed() == "Index over!")
    {
            ui->m_createIndexBtn->setText(tr("索引完毕！"));
            ui->m_createIndexBtn->setEnabled(true);
            return;
    }

    //搜索结果处理
    qDebug() << strImagesPath << endl;
    ShowResult(strImagesPath);
}


/**
 *  @brief  显示检索结果
 *  @name
 *  @author lesliefish
 *  @param strPath 返回结果的图像数据集的路径
 *  @return
 */
void MainWindow::ShowResult(const QString& strPath)
{
    QStringList filepathList = strPath.split("CSU");
    filepathList.removeLast();//去掉最后的换行空白
    //第k幅图像
    int k = 0;
    for(int i = 0; i < ui->m_showTableWidget->rowCount(); i++)
    {
        for(int j = 0; j < ui->m_showTableWidget->columnCount(); j++)
        {
            QLabel *label = new QLabel();
            label->setScaledContents(true);
            QImage* img = new QImage();
            img->load(filepathList[k]);
            label->setPixmap(QPixmap::fromImage(*img));
            label->setToolTip(filepathList[k]+"");
            ui->m_showTableWidget->setCellWidget(i,j,label);
            k++;
        }
    }
}

/**
 *  @brief  显示本地图片到窗口上
 *  @name
 *  @author lesliefish
 *  @param path 文件路径
 *  @return
 */
void MainWindow::ShowChoosedImage(const QString& path)
{
    ui->m_choosedImageWidget->setStyleSheet("QWidget{border-image: url(" + path + ")}");
    ui->m_choosedImageWidget->setToolTip(path);
}



