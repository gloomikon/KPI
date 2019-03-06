package com.company;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        //Create Turniket
        Turniket turniket = new Turniket();

        //Fill DataBase with Cards
        for (int i = 0; i < 10; i++)
        {
            Card card = Card.createCard(1);
            DataBase.getInstance().addCard(card);
        }

        //Get Card from DataBasa
        Card myCard = DataBase.getInstance().getCard(1);
        turniket.checkCard(myCard);
        myCard.addRides(3);

        turniket.checkCard(myCard);
        turniket.checkCard(myCard);
        turniket.checkCard(myCard);
        turniket.checkCard(myCard);

        turniket.getInfo();
    }
}
