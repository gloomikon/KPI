#include <fstream>
#include <math.h>
#include <cstdlib>

using namespace std;

struct triangle {
    double a;
    double b;
    double c;
};

triangle *g_arr;
unsigned int g_n;

int check_triangle(double a, double b, double c)
{
    if (a >= b + c || b >= a + c || c >= a + b)
        return (0);
    else
        return (1);
}

double find_bisection(double a, double b, double c)
{
    double bisection;

    bisection = sqrt(a * b * (a + b + c) * (a + b - c)) / (a + b);
    return (bisection);
}

void print_file(string fileName)
{
    ofstream file;

    file.open(fileName);
    for (unsigned int i = 0; i < g_n; i++)
    {
        if (!check_triangle(g_arr[i].a, g_arr[i].b, g_arr[i].c))
            file << "0.0 0.0 0.0\n";
        else
            file << find_bisection(g_arr[i].a, g_arr[i].b, g_arr[i].c) << " " <<
                    find_bisection(g_arr[i].a, g_arr[i].c, g_arr[i].b) << " " <<
                    find_bisection(g_arr[i].b, g_arr[i].c, g_arr[i].a) << endl;
    }
    file.close();
}

void read_info(string fileName)
{
    ifstream file;

    file.open(fileName);
    file >> g_n;
    g_arr = reinterpret_cast<triangle*>(malloc(sizeof(triangle) * g_n));
    for (unsigned int i = 0; i < g_n; i++)
        file >> g_arr[i].a >> g_arr[i].b >> g_arr[i].c;
    file.close();
}

int main()
{
    read_info("C://Users/gloomikon/Documents/Lab1/input.txt");
    print_file("C://Users/gloomikon/Documents/Lab1/output.txt");
    delete [] g_arr;
    return (0);
}
