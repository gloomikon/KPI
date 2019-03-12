package com.company;

import java.util.ArrayList;

public class Road
{
    private ArrayList<Vehicle> vehicles;

    Road()
    {
        this.vehicles = new ArrayList<Vehicle>();
    }

    public int getCountOfHumans()
    {
        int count = 0;

        for (Vehicle v : vehicles)
            count += v.getOccupiedSeats();

        return count;
    }

    public void addCarToRoad(Vehicle v)
    {
        if (vehicles.contains(v))
            throw new NullPointerException();
        else vehicles.add(v);
    }
}
