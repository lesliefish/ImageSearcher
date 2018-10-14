#pragma once

#include <QMouseEvent>
#include <QWidget>
namespace Ui { class FramelessWidget; };

namespace imagesearcher
{
    class FramelessWidget : public QWidget
    {
        Q_OBJECT

    public:
        explicit FramelessWidget(QWidget *parent = Q_NULLPTR);
        ~FramelessWidget();

    protected:
        QPoint m_pointMove{ QPoint() };
        bool m_dragWindow{ false };
        virtual void mousePressEvent(QMouseEvent *e);
        virtual void mouseMoveEvent(QMouseEvent *e);
        virtual void mouseReleaseEvent(QMouseEvent *event);

    private:
        Ui::FramelessWidget *ui;
    };

}

