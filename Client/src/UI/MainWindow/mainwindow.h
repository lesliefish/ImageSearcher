#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QWidget>
#include <QtNetwork/QTcpSocket>
#include "WarningDlg/warningdlg.h"
#include "FramelessWidget/framelesswidget.h"
#include "About/aboutdlg.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public FramelessWidget
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    //打开的文件路径
    QString m_openedFilePath;
signals:
    void SigOpenFileSuccess(QString);
private:
    void InitUI();
    void InitConnect();

    //发送数据
    void SendRequest(QString& strAction, QString& filepath, QString& depends);
    //读数据
    void ReadMessage();
    //搜索结果展示在下面表中
    void ShowResult(const QString&);

    //打开一个图像文件
    bool OpenImageFile();
    //显示打开的图片
    void ShowChoosedImage(const QString&);
    WarningDlg m_warningDlg;
    AboutDlg m_aboutDlg;

    QTcpSocket* m_pSocket;


};

#endif // MAINWINDOW_H
