package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataBase
{
    private static final DataBase instance = new DataBase();

    private List<Card> cardList;

    private DataBase()
    {
        this.cardList = new ArrayList<Card>();
        for (int i = 0; i < 100; i++)
        {
            Card card = Card.createCard(ThreadLocalRandom.current().nextInt(1, 4), this.cardList.size() + 1);
            this.cardList.add(card);
        }
    }
    public static DataBase getInstance()
    {
        return instance;
    }
    public int getCardsNumber()
    {
        return this.cardList.size();
    }
    public void addCard(Card card)
    {
        this.cardList.add(card);
    }
    public Card getCard(int choice)
    {
        if (choice == 1)
        {
            for (Card card : cardList)
                if (card.getType().equals("school") && card.getStatus() == false)
                {
                    card.setStatus(true);
                    return card;
                }
        }
        else if (choice == 2)
        {
            for (Card card : cardList)
                if (card.getType().equals("student") && card.getStatus() == false)
                {
                    card.setStatus(true);
                    return card;
                }
        }
        else if (choice == 3)
        {
            for (Card card : cardList)
                if (card.getType().equals("stash") && card.getStatus() == false)
                {
                    card.setStatus(true);
                    return card;
                }
        }

        return null;
    }
}
