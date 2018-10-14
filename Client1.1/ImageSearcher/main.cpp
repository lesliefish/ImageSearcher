#include "MainWindow.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    imagesearcher::MainWindow w;
    w.show();
    return a.exec();
}
