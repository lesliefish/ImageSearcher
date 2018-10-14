#pragma once

#include "FramelessWidget.h"
#include <QWidget>
#include <QTcpSocket>
namespace Ui { class MainWindow; };

namespace imagesearcher 
{
    class MainWindow : public FramelessWidget
    {
        Q_OBJECT

    public:
        explicit MainWindow(QWidget *parent = Q_NULLPTR);
        ~MainWindow();

    private:
        // 初始化界面
        void initUi();
        // 初始化信号槽
        void initConnection();
        // 发送请求
        void sendRequest(const QString& action, const QString& filePath, const QString& depends);
        // 接收请求
        void recieveFromSever();
        // show result 
        void showResult(const QStringList paths);
    private:
        Ui::MainWindow *ui;

        QString m_curImagePath{};
        QTcpSocket m_socket;
    };
}


