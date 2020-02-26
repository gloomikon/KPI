package com.company;

import java.util.ArrayList;

public class Vehicle<T extends Man>
{
    private final int seats;
    private ArrayList<T> passagers;

    Vehicle(int seats)
    {
        this.seats = seats;
        this.passagers = new ArrayList<T>(seats);
    }
    int getSeats()
    {
        return this.seats;
    }

    int getOccupiedSeats()
    {
        return this.passagers.size();
    }
    void pickUp(T t)
    {
        if (passagers.contains(t))
            throw new NullPointerException();
        else if (this.passagers.size() == this.seats)
            throw new IndexOutOfBoundsException();
        else passagers.add(t);

    }
    void getOut(T t)
    {
        if (passagers.contains(t))
            passagers.remove(t);
        else
            throw new NullPointerException();
    }

}
