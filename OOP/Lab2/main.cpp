#include "image.h"
#include "mpool.h"
using namespace std;

int main()
{
    cout << "--START--" << endl;
    mPool *pool = new mPool(sizeof(Picture) + sizeof(Pixel) * WIDTH * HEIGHT, 4);
    cout << "Pool created successfully" << endl;
    Picture *a = pool_new(pool);
    if (a)
    {
        create_black(a);
        save_to_file("C://Users/gloomikon/Documents/black.ppm", a);
    }

    Picture *b = pool_new(pool);
    if (b)
    {
        create_white(b);
        save_to_file("C://Users/gloomikon/Documents/white.ppm", b);
    }

    Picture *c = pool_new(pool);
    if (c)
    {
        create_color(c);
        save_to_file("C://Users/gloomikon/Documents/color.ppm", c);
    }

    Picture *d = pool_new(pool);
    if (d)
    {
        read_from_file("C://Users/gloomikon/Documents/borabora.ppm", d);
        save_to_file("C://Users/gloomikon/Documents/output_barabora.ppm", d);
    }

    Picture *e = pool_new(pool);
    if (e)
    {
        read_from_file("C://Users/gloomikon/Documents/cayuga.ppm", e);
        save_to_file("C://Users/gloomikon/Documents/output_cayuga.ppm", e);
    }

    pool_delete(pool, b);
    pool_delete(pool, d);

    Picture *f = pool_new(pool);
    f->pixels = reinterpret_cast<Pixel*>(reinterpret_cast<char*>(f) + sizeof(Picture));
    if (f)
    {
        read_from_file("C://Users/gloomikon/Documents/cayuga.ppm", f);
        save_to_file("C://Users/gloomikon/Documents/output_cayuga.ppm", f);
    }
    Picture *g = pool_new(pool);
    g->pixels = reinterpret_cast<Pixel*>(reinterpret_cast<char*>(g) + sizeof(Picture));
    if (g)
    {
        read_from_file("C://Users/gloomikon/Documents/sage.ppm", g);
        save_to_file("C://Users/gloomikon/Documents/output_sage.ppm", g);
    }
    cout << "--END--" << endl;
    return 0;
}
