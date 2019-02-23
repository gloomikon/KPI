#include <iostream>
#ifndef IMAGE_H
#define IMAGE_H

const unsigned int WIDTH = 800;
const unsigned int HEIGHT = 600;

struct Pixel {
    char R, G, B;
};

struct Picture{
    Pixel *pixels = nullptr;
};

void create_white(Picture*);
void create_black(Picture*);
void create_color(Picture*);
void save_to_file(std::string, Picture*);
void read_from_file(std::string, Picture*);

#endif // IMAGE_H
