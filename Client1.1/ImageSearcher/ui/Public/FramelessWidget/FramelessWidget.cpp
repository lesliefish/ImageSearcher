#include "FramelessWidget.h"
#include "ui_FramelessWidget.h"
#include "QAbstractItemDelegate"
#include <QPainter>

namespace imagesearcher
{
    FramelessWidget::FramelessWidget(QWidget *parent)
        : QWidget(parent)
    {
        ui = new Ui::FramelessWidget();
        ui->setupUi(this);
        setWindowFlags(windowFlags() | Qt::FramelessWindowHint);
    }

    FramelessWidget::~FramelessWidget()
    {
        delete ui;
    }

    void FramelessWidget::mousePressEvent(QMouseEvent *event)
    {
        if (event->button() == Qt::LeftButton)
        {
            m_pointMove = event->globalPos() - pos();
            event->accept();
            m_dragWindow = true;
        }
    }

    void FramelessWidget::mouseMoveEvent(QMouseEvent *event)
    {
        if ((event->buttons() & Qt::LeftButton) && m_dragWindow)
        {
            move(event->globalPos() - m_pointMove);
            event->accept();
        }
    }

    void FramelessWidget::mouseReleaseEvent(QMouseEvent *event)
    {
        m_dragWindow = false;
    }

    void FramelessWidget::paintEvent(QPaintEvent *event)
    {
        QPainter paint(this);
        paint.begin(this);
        QColor backgroundColor = QColor("#FFFFFF");
        // 透明度
        backgroundColor.setAlpha(0.7 * 255);

        // 反走样
        paint.setRenderHint(QPainter::Antialiasing, true);
        paint.setPen(QColor("#333333"));
        paint.setBrush(QBrush(backgroundColor, Qt::SolidPattern));//设置画刷形式 
        paint.drawRoundedRect(0, 0, width(), height(), 0, 0, Qt::AbsoluteSize);
    }

}

