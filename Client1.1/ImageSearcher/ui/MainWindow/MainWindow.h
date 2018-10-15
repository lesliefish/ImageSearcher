/** @file   MainWindow.h
  * @note   程序主窗口
  * 
  * @brief  
  * @author lesliefish
  * @date   2018/10/15
  */
#pragma once

#include "ui/Public/InfoWidget/InfoTextWidget.h"
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
        // 初始化索引/检索依据数据
        void initCommboxDatas();
        // 初始化信号槽
        void initConnection();
        // 发送请求
        void sendRequest(const QString& action, const QString& filePath, const QString& depends);
        // 接收请求
        void recieveFromSever();
        // 检索
        void search();
        // 创建索引
        void createIndex();
        // 显示搜索结果
        void showResult(const QStringList paths);
        // 选择文件
        void chooseFile();

    protected:
        virtual void resizeEvent(QResizeEvent* event);
    private:
        Ui::MainWindow *ui;
        InfoTextWidget* infoTextWidget{ nullptr };
        QString m_curImagePath{};
        QTcpSocket m_socket;
    };
}


