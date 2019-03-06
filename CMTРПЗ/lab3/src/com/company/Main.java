package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Turniket turniket = new Turniket();
        Card card = new SchoolCard(10);
        turniket.checkCard(card);
        turniket.checkCard(card);
        turniket.checkCard(card);
        card.addRides(2);
        turniket.checkCard(card);
        turniket.checkCard(card);
        turniket.checkCard(card);
        card.addDays(2);
        card.addRides(2);
        turniket.checkCard(card);
        turniket.checkCard(card);
        card.setDate(4,1,2000);
        turniket.checkCard(card);
        turniket.checkCard(card);
        turniket.checkCard(card);
        turniket.checkCard(card);
        turniket.getInfo();


    }
}
