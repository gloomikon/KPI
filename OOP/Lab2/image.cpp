#include "image.h"
#include <iostream>
#include <fstream>
using namespace std;

void create_white(Picture *picture)
{
    Pixel white;
    white.R = static_cast<char>(255);
    white.G = static_cast<char>(255);
    white.B = static_cast<char>(255);
    for (unsigned int i = 0; i < HEIGHT; i++)
        for (unsigned int j = 0; j < WIDTH; j++)
            picture->pixels[i * WIDTH + j] = white;
}

void create_black(Picture *picture)
{
    Pixel black;
    black.R = static_cast<char>(0);
    black.G = static_cast<char>(0);
    black.B = static_cast<char>(0);
    for (unsigned int i = 0; i < HEIGHT; i++)
        for (unsigned int j = 0; j < WIDTH; j++)
        {
            picture->pixels[i * WIDTH + j] = black;
        }
}


void create_color(Picture *picture)
{
    Pixel color;
    for (unsigned int i = 0; i < HEIGHT; i++)
        for (unsigned int j = 0; j < WIDTH; j++)
        {
            color.R = static_cast<char>(i * j);
            color.G = static_cast<char>(i * j);
            color.B = static_cast<char>(i * j);
            picture->pixels[i * WIDTH + j] = color;
        }
}

void save_to_file(string file_name, Picture *picture)
{
    ofstream file;
    file.open(file_name, ios::binary);
    file << "P6\n" << WIDTH << " " << HEIGHT << "\n255\n";
    for (unsigned int i = 0; i < HEIGHT; i++)
        for (unsigned int j = 0; j < WIDTH; j++)
            file << picture->pixels[i * WIDTH + j].R <<
                    picture->pixels[i * WIDTH + j].G <<
                    picture->pixels[i * WIDTH + j].B;
    file.close();
}

void read_from_file(string file_name, Picture *picture)
{
    ifstream file;
    Pixel pixel;

    file.open(file_name, ios::binary);
    file.ignore(15);
    for (unsigned int i = 0; i < HEIGHT; i++)
        for (unsigned int j = 0; j < WIDTH; j++)
        {
            file.read(&pixel.R, 1);
            file.read(&pixel.G, 1);
            file.read(&pixel.B, 1);
            picture->pixels[i * WIDTH + j] = pixel;
        }
}
