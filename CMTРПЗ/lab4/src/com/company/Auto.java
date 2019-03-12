package com.company;

public class Auto<T extends Man> extends Vehicle<T>
{
    Auto(int seats)
    {
        super(seats);
    }
}
