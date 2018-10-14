/** @file   InfoWidget.h
  *
  * @brief  信息窗口基类  主要用于PC端各弹出界面
  * @author yulei
  * @date   2018/07/06
  */
#pragma once
#include "FramelessWidget.h"
namespace Ui
{
    class InfoWidget;
};

namespace imagesearcher
{
    class InfoWidget : public FramelessWidget
    {
        Q_OBJECT

    public:
        InfoWidget(QWidget *parent = Q_NULLPTR);
        ~InfoWidget();

        // 中心界面指针
        QWidget* getCenterPage();
        // 隐藏底部两个按钮
        void hideBottomBtn();
        // 设置窗口标题
        void setPageTitle(const QString& title);
        // 设置确定按钮文本
        void setOKBtnText(const QString& text);
        void setOKBtnVisible(bool visible);
        // 设置取消按钮文本
        void setCancelBtnText(const QString& text);
        void setCancelBtnVisible(bool visible);

        void setIgnoreBtnText(const QString& text);
        void setIgnoreBtnVisible(bool bShow);
        void setCheckBoxText(const QString& text);
        void setCheckBoxVisible(bool visible);
        bool getCheckedState();
    signals:
        void signalOK();
        void signalCancel();
        void signalIgnore();
        void signalClose();

    private:
        void init();

    private:
        Ui::InfoWidget *ui;
    };

}

