#include "widget.h"
#include "ui_widget.h"

static bool mouseClicked = false;

Widget::Widget(QWidget *parent):QWidget(parent), ui(new Ui::Widget)
{
    ui->setupUi(this);
    this->setFixedSize(800,600);
    QTimer *timer = new QTimer(this);
    connect(timer, SIGNAL(timeout()), this, SLOT(update()));
    timer->start(1000);
    CtrlC = new QShortcut(this);
    CtrlC->setKey(Qt::CTRL + Qt::Key_C);
    connect(CtrlC, SIGNAL(activated()), this, SLOT(slotShortcutCtrlC()));
}

void Widget::slotShortcutCtrlC()
{
    mouseClicked = true;
    update();
}

void Widget::paintEvent(QPaintEvent *)
{
    static bool firstUsage = true;
    const int HEIGHT = this->height() / 6;
    const int WIDTH = this->width() / 8;
    static QColor colors[48];
    QPainter painter(this);
    QRect rect;
    QPen pen;
    QBrush brush;
    QColor color;
    bool f = false;

    pen.setWidth(0);

    for (int i = 0; i < 6; i++)
        for (int j = 0; j < 8; j++)
        {
            rect.setCoords(0 + j * WIDTH,
                           0 + i * HEIGHT,
                           0 + (j + 1) * WIDTH,
                           0 + (i + 1) * HEIGHT);
            if ((!mouseClicked && i < 3) || (mouseClicked && i >= 3) || firstUsage)
            {
                color = QColor::fromRgb(QRandomGenerator::global()->generate());
                for (int k = 0; k < 48; k++)
                    if (colors[k] == color)
                    {
                        f = true;
                        break;
                    }
                while (f)
                {
                    f = false;
                    color = QColor::fromRgb(QRandomGenerator::global()->generate());
                    for (int k = 0; k < 48; k++)
                        if (colors[k] == color)
                        {
                            f = true;
                            break;
                        }
                }
                colors[i * 8 + j] = color;
            }
            brush.setColor(colors[i * 8 + j]);
            brush.setStyle(Qt::SolidPattern);
            painter.drawRect(rect);
            painter.fillRect(rect, brush);
        } //end of loop
    if (firstUsage)
    {
        firstUsage = false;
    }
    if (mouseClicked)
    {
        mouseClicked = false;
    }
}
void Widget::mousePressEvent(QMouseEvent *event)
{
    if(event->button() == Qt::RightButton)
    {
        mouseClicked = true;
        update();
    }

}

Widget::~Widget()
{
    delete ui;
}
