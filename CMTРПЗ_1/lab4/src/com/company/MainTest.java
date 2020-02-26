package com.company;

import org.junit.Assert;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void testCapacity()
    {
        Bus bus = new Bus();
        int busCapacity = bus.getSeats();
        int seats = 30;
        Assert.assertEquals(busCapacity, seats);
    }

    @org.junit.Test
    public void testOccupiedSeats()
    {
        Bus bus = new Bus();
        for (int i = 0; i < 10; i++)
        {
            Man man = new Man(1, "test");
            bus.pickUp(man);
        }
        int occupiedSeats = bus.getOccupiedSeats();
        int seats = 10;
        Assert.assertEquals(occupiedSeats, seats);
    }

    @org.junit.Test
    public void checkPickUpTrue()
    {
        Taxi taxi = new Taxi();
        boolean result;
        try
        {
            for (int i = 0; i < 2; i++)
            {
                Man man = new Man(1, "test");
                taxi.pickUp(man);
            }
            result = true;
        }
        catch (IndexOutOfBoundsException e)
        {
            result = false;
        }
        Assert.assertEquals(result, true);
    }

    @org.junit.Test(expected = IndexOutOfBoundsException.class)
    public void checkPickUpFalse()
    {
        Taxi taxi = new Taxi();

        for (int i = 0; i < 7; i++)
        {
            Man man = new Man(1, "test");
            taxi.pickUp(man);
        }
    }

    @org.junit.Test
    public void checkDoublePickUpFalse()
    {
        Taxi taxi = new Taxi();
        boolean result;
        try
        {
            Man strawberry = new Man(19, "strawberry");
            taxi.pickUp(strawberry);
            taxi.pickUp(strawberry);
            result = true;
        } catch (NullPointerException e) {
            result = false;
        }
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void checkGetOutTrue()
    {
        Taxi taxi = new Taxi();
        boolean result;
        try
        {
            Man kolumbia = new Man(19, "Nikolay Zhurba");
            taxi.pickUp(kolumbia);
            taxi.getOut(kolumbia);
            result = true;
        }
        catch (NullPointerException e)
        {
            result = false;
        }
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void checkDoubleGetOutFalse()
    {
        Taxi taxi = new Taxi();
        boolean result;
        try
        {
            Man kolumbia = new Man(19, "Nikolay Zhurba");
            taxi.pickUp(kolumbia);
            taxi.getOut(kolumbia);
            taxi.getOut(kolumbia);
            result = true;
        }
        catch (NullPointerException e)
        {
            result = false;
        }
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void checkInvalidPersonGetOutTrue()
    {
        Taxi taxi = new Taxi();
        boolean result;
        try
        {
            Man kolumbia = new Man(19, "Nikolay Zhurba");
            taxi.pickUp(kolumbia);
            Man bt = new Man(19, "Bethany Tusen");
            taxi.getOut(bt);
            result = true;
        }
        catch (NullPointerException e)
        {
            result = false;
        }
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void checkAddToRoadTrue()
    {
        Road road = new Road();
        Taxi taxi = new Taxi();
        Bus bus = new Bus();
        boolean result;
        try
        {
            road.addCarToRoad(taxi);
            road.addCarToRoad(bus);
            result = true;
        }
        catch (NullPointerException e)
        {
            result = false;
        }
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void checkDoubleAddToRoadFalse()
    {
        Road road = new Road();
        Taxi taxi = new Taxi();
        Bus bus = new Bus();
        boolean result;
        try
        {
            road.addCarToRoad(taxi);
            road.addCarToRoad(bus);
            road.addCarToRoad(taxi);
            result = true;
        }
        catch (NullPointerException e)
        {
            result = false;
        }
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void checkPeopleOnRoad()
    {
        Road road = new Road();

        Bus bus = new Bus();
        for (int i = 0; i < 15; i++)
        {
            Man man = new Man(1, "test");
            bus.pickUp(man);
        }
        Taxi taxi = new Taxi();
        taxi.pickUp(new Man(1, "test"));
        taxi.pickUp(new Man(1, "test"));

        PoliceCar policeCar = new PoliceCar();
        policeCar.pickUp(new PoliceMan(1, "test"));

        road.addCarToRoad(bus);
        road.addCarToRoad(taxi);
        road.addCarToRoad(policeCar);
        Assert.assertEquals(road.getCountOfHumans(), 18);
    }
}