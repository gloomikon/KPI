package com.company;
import java.util.Date;
import java.util.Calendar;

public class SchoolCard extends Card
{
    private Date finDate;
    private int rides;
    public SchoolCard(int number)
    {
        super("school", number);
        this.rides = 0;
        this.finDate = null;
    }
    @Override
    public void addRides(int rides)
    {
        this.rides += rides;
    }
    @Override
    public void addDays(int days)
    {
        Date currentDate;
        if (this.finDate == null)
            currentDate = new Date();
        else
            currentDate = this.finDate;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.DATE, days);
        this.finDate = c.getTime();
    }
    @Override
    public void addMonths(int months)
    {
        Date currentDate;
        if (this.finDate == null)
            currentDate = new Date();
        else
            currentDate = this.finDate;
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        c.add(Calendar.MONTH, months);
        this.finDate = c.getTime();
    }
    @Override
    public boolean checkPass()
    {
        if (finDate != null)
        {
            Date currentDay = new Date();
            if (!currentDay.after(this.finDate))
                return true;
        }
        if (this.rides > 0)
        {
            this.rides--;
            return true;
        }
        return false;
    }
    @Override
    public void setDate(int date, int month, int year)
    {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DATE, date);
        this.finDate = c.getTime();
    }

    @Override
    public void addCash(int cash)
    {
        //doNothing
    }
}
