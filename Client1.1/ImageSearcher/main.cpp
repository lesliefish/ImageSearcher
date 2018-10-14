#include "MainWindow.h"
#include "ui/Public/InfoWidget/InfoWidget.h"
#include <QtWidgets/QApplication>

using namespace imagesearcher;
int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    imagesearcher::MainWindow w;
    w.show();

    InfoWidget i;
    i.show();
    return a.exec();
}
