package com.company;

import org.junit.Assert;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class MainTest {

    //Create Turniket
    Turniket turniket = new Turniket();

    @org.junit.Test
    public void test_school_rides_true()
    {
        Card myCard = DataBase.getInstance().getCard(1);
        myCard.addRides(1);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void test_school_rides_false()
    {
        Card myCard = DataBase.getInstance().getCard(1);
        myCard.addRides(1);
        turniket.checkCard(myCard);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void test_student_time_true()
    {
        Card myCard = DataBase.getInstance().getCard(2);
        myCard.addDays(3);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void test_student_time_false()
    {
        Card myCard = DataBase.getInstance().getCard(2);
        myCard.addDays(3);
        myCard.setDate(4,1,2000);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void test_student_rides_after_time_true()
    {
        Card myCard = DataBase.getInstance().getCard(2);
        myCard.addDays(3);
        myCard.addRides(1);
        turniket.checkCard(myCard);
        myCard.setDate(23,1,2000);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void test_student_rides_after_time_false()
    {
        Card myCard = DataBase.getInstance().getCard(2);
        myCard.addDays(3);
        myCard.addRides(1);
        turniket.checkCard(myCard);
        myCard.setDate(23,1,2000);
        turniket.checkCard(myCard);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void test_stash_true_one_ride()
    {
        Card myCard = DataBase.getInstance().getCard(3);
        myCard.addCash(8);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void test_stash_true_two_rides()
    {
        Card myCard = DataBase.getInstance().getCard(3);
        myCard.addCash(19);
        turniket.checkCard(myCard);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, true);
    }

    @org.junit.Test
    public void test_stash_false_one_ride()
    {
        Card myCard = DataBase.getInstance().getCard(3);
        myCard.addCash(5);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, false);
    }

    @org.junit.Test
    public void test_stash_false_two_rides()
    {
        Card myCard = DataBase.getInstance().getCard(3);
        myCard.addCash(13);
        turniket.checkCard(myCard);
        boolean result = turniket.checkCard(myCard);
        Assert.assertEquals(result, false);
    }
}