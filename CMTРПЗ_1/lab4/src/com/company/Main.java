package com.company;


public class Main
{
    public static void main(String[] arvg)
    {
        //Create Vehicles
        Bus bus = new Bus();
        Taxi taxi = new Taxi();
        PoliceCar policeCar = new PoliceCar();
        FireEngine fireEngine = new FireEngine();

        //Create Men
        Man bt = new Man(19, "Bethany Tusen");
        FireFighter kolumbia = new FireFighter(19, "Nikolay Zhurba");
        PoliceMan strawberry = new PoliceMan(19, "Strawberry");

        //Test
        bus.pickUp(bt);                     //OK
        bus.pickUp(kolumbia);               //OK
        bus.pickUp(strawberry);             //OK

        taxi.pickUp(bt);                    //OK
        taxi.pickUp(kolumbia);              //OK
        taxi.pickUp(strawberry);            //OK

        //policeCar.pickUp(bt);             //Error
        //policeCar.pickUp(kolumbia);       //Error
        policeCar.pickUp(strawberry);       //OK

        //fireEngine.pickUp(bt);            //Error
        fireEngine.pickUp(kolumbia);        //OK
        //fireEngine.pickUp(strawberry);    //Error

    }
}
