package com.company;

import java.util.ArrayList;
import java.util.List;

public class DataBase
{
    private List<Card> cardList;

    private static final DataBase instance = new DataBase();

    private DataBase()
    {
        this.cardList = new ArrayList<Card>();
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
}
