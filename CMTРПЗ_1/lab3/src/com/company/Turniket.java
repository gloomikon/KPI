package com.company;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Pass
{
    private final Date date;
    private final String cardType;
    private final int cardNumber;
    private final boolean result;
    public Pass(Date date, String cardType, int cardNumber, boolean result)
    {
        this.date = date;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
        this.result = result;
    }

    public Date getDate()
    {
        return this.date;
    }

    public String getCardType()
    {
        return this.cardType;
    }

    public int getCardNumber()
    {
        return this.cardNumber;
    }
    public boolean getResult()
    {
        return this.result;
    }
}

public class Turniket
{
    private List<Pass> passes;
    public Turniket()
    {
        this.passes = new ArrayList<Pass>();
    }
    public boolean checkCard(Card card)
    {
        Date currentDate = new Date();
        String cardType = card.getType();
        int cardNumber = card.getNumber();
        boolean result = card.checkPass();
        Pass pass = new Pass(currentDate, cardType, cardNumber, result);
        this.passes.add(pass);
        return result;
    }
    public void getInfo()
    {
        for (Pass pass : this.passes)
        {
            System.out.print("Date: " + pass.getDate() + " Card type: " + pass.getCardType() + " Card №" + pass.getCardNumber());
            if (pass.getResult())
                System.out.println(" Success");
            else
                System.out.println(" Failure");
        }
    }
    public void getInfo(int choice)
    {
        for (Pass pass : this.passes)
        {
            if (    (choice == 1 && pass.getCardType() == "school") ||
                    (choice == 2 && pass.getCardType() == "student") ||
                    (choice == 3 && pass.getCardType() == "stash"))
            {
                System.out.print("Date: " + pass.getDate() + " Card type: " + pass.getCardType() + " Card №" + pass.getCardNumber());
                if (pass.getResult())
                    System.out.println(" Success");
                else
                    System.out.println(" Failure");
            }

        }
    }
}
