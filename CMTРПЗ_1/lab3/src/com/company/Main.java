package com.company;
import javax.xml.crypto.Data;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args)
    {
        //Create Turniket
        Turniket turniket = new Turniket();


        //Get Card from DataBase
        Card schoolCard1 = DataBase.getInstance().getCard(1);
        Card schoolCard2 = DataBase.getInstance().getCard(1);
        Card studentCard1 = DataBase.getInstance().getCard(2);
        Card stashCard1 = DataBase.getInstance().getCard(3);


        //Make some actions
        turniket.checkCard(schoolCard1);
        schoolCard1.addRides(3);
        stashCard1.addCash(17);
        turniket.checkCard(schoolCard1);
        turniket.checkCard(schoolCard1);
        turniket.checkCard(stashCard1);
        turniket.checkCard(schoolCard1);
        turniket.checkCard(schoolCard2);
        schoolCard2.addRides(3);
        turniket.checkCard(schoolCard2);
        turniket.checkCard(schoolCard1);
        turniket.checkCard(studentCard1);
        turniket.checkCard(stashCard1);
        studentCard1.addDays(1);
        turniket.checkCard(studentCard1);
        turniket.checkCard(stashCard1);

        //Get full info
        turniket.getInfo();

        //Get school card info
        System.out.println();
        turniket.getInfo(1);

        //Get student card info
        System.out.println();
        turniket.getInfo(2);

        //Get stash card info
        System.out.println();
        turniket.getInfo(3);

    }
}
