package com.company;
import java.util.Date;

abstract public class Card
{
    private final String type;
    private final int number;
    private boolean status;
    public Card(String type, int number)
    {
        this.type = type;
        this.number = number;
        this.status = false;
    }
    public String getType()
    {
        return this.type;
    }
    public int getNumber()
    {
        return this.number;
    }
    public boolean getStatus()
    {
        return this.status;
    }
    public void setStatus(boolean status)
    {
        this.status = status;
    }
    static public Card createCard(int choice, int number)
    {
        Card card;
        if (choice == 1)
            card = new SchoolCard(number);
        else if (choice == 2)
            card = new StudentCard(number);
        else if (choice == 3)
            card = new StashCard(number);
        else
            card = null;
        return card;
    }
    abstract public boolean checkPass();
    abstract public void addRides(int rides);
    abstract public void addDays(int days);
    abstract public void addCash(int cash);
    abstract public void addMonths(int months);
    abstract public void setDate(int date, int month, int year);
}
