#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QFile>
#include <QDir>
#include <QFileDialog>
#include <QPalette>
#include <QDebug>
#include <QIcon>
#include <QWidget>
#include <QtNetwork/QTcpSocket>
#include "WarningDlg/warningdlg.h"
#include "FramelessWidget/FramelessWidget.h"
#include "About/AboutDlg.h"

namespace Ui 
{
class MainWindow;
}

class MainWindow : public FramelessWidget
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

signals:
    void runOver();

private:
    void initUi();
    void initConnect();

    //发送数据
    void sendRequest(QString& strAction, QString& filepath, QString& depends);
    //读数据
    void readMessage();
    //搜索结果展示在下面表中
    void showResult(const QString&);

    //打开一个图像文件
    bool openImageFile();
    //显示打开的图片
    void showChoosedImage(const QString&);

private:
	Ui::MainWindow *ui;
	//打开的文件路径
	QString m_openedFilePath;
	WarningDlg* m_warningDlg{ nullptr };
	AboutDlg* m_aboutDlg{nullptr};
	QTcpSocket* m_pSocket;
};

#endif // MAINWINDOW_H
