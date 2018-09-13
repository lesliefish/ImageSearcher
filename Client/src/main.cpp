#include "MainWindow/MainWindow.h"
#include <QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    a.setApplicationName(QObject::tr("Í¼Ïñ¼ìË÷ÏµÍ³"));
    MainWindow w;
    w.show();

    return a.exec();
}
