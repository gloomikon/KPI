package com.company;
import java.util.Date;
import java.util.Calendar;


public class StashCard extends Card
{
    private int cash;
    static final private int PRICE = 8;
    public StashCard(int number)
    {
        super("stash", number);
        this.cash = 0;
    }
    @Override
    public void addCash(int cash)
    {
        this.cash += cash;
    }

    @Override
    public boolean checkPass()
    {
        if (this.cash >= PRICE)
        {
            this.cash -= PRICE;
            return true;
        }
        return false;
    }

    @Override
    public void addDays(int days)
    {
        //doNothing
    }

    @Override
    public void addMonths(int months)
    {
        //doNothing
    }

    @Override
    public void addRides(int rides)
    {
        //doNothing
    }
    @Override
    public void setDate(int date, int month, int year)
    {
        //doNothing
    }
}
