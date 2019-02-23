#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QShortcut>
#include <QPainter>
#include <QRandomGenerator>
#include <QTimer>
#include <QMouseEvent>

namespace Ui
{
class Widget;
}

class Widget : public QWidget
{
     Q_OBJECT

public:
    explicit Widget(QWidget *parent = nullptr);
    ~Widget();
private:
    Ui::Widget *ui;
    QShortcut *CtrlC;
private slots:
    void slotShortcutCtrlC();
protected:
    void paintEvent(QPaintEvent *);
    void mousePressEvent(QMouseEvent *);

};

#endif
