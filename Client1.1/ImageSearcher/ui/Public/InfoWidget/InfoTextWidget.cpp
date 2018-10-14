/** @file    InfoTextWidget
  * @note    HangZhou Hikvision System Technology Co., Ltd. All Right Reserved.
  * @brief   PC端中间带有一个文本Label的提示框
  *
  * @author  xuanlushan
  * @date    2018/08/30
  *
  * @note    detailed functional description of this document and comments
  * @note    History
  *
  * @warning warning message related to this document 
  */
#include "InfoTextWidget.h"
#include "ui_InfoTextWidget.h"

namespace imagesearcher
{
    InfoTextWidget::InfoTextWidget(QWidget *parent)
        : InfoWidget(parent)
    {
        ui = new Ui::InfoTextWidget();
        ui->setupUi(getCenterPage());
        setWindowFlags(windowFlags() | Qt::SubWindow);
        setAttribute(Qt::WA_ShowModal, true);
        setIgnoreBtnVisible(false);
    }

    InfoTextWidget::~InfoTextWidget()
    {
        delete ui;
    }

    void InfoTextWidget::setTipText(const QString& text)
    {
        ui->labelTipText->setText(text);
    }
}

