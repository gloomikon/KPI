package com.company;

import org.junit.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void sortByLengthFourStrings()
    {
        ArrayList<String> s1 = new ArrayList<String>(Arrays.asList("123", "12", "123456", "1"));
        Main.sortByLength(s1);
        ArrayList<String> s2 = new ArrayList<String>(Arrays.asList("1", "12", "123", "123456"));
        Assert.assertEquals(s1, s2);
    }

    @org.junit.Test
    public void sortByLengthWhiteSpacesWords()
    {
        ArrayList<String> s1 = new ArrayList<String>(Arrays.asList("   ", "12", "123456", " "));
        Main.sortByLength(s1);
        ArrayList<String> s2 = new ArrayList<String>(Arrays.asList(" ", "12", "   ", "123456"));
        Assert.assertEquals(s1, s2);
    }

    @org.junit.Test
    public void sortByLengthEmptyWord()
    {
        ArrayList<String> s1 = new ArrayList<String>(Arrays.asList("   ", "12", "123456", ""));
        Main.sortByLength(s1);
        ArrayList<String> s2 = new ArrayList<String>(Arrays.asList("", "12", "   ", "123456"));
        Assert.assertEquals(s1, s2);
    }

    @org.junit.Test
    public void sortByLengthEmptyWords()
    {
        ArrayList<String> s1 = new ArrayList<String>(Arrays.asList("", "", "", ""));
        Main.sortByLength(s1);
        ArrayList<String> s2 = new ArrayList<String>(Arrays.asList("", "", "", ""));
        Assert.assertEquals(s1, s2);
    }

    @org.junit.Test
    public void sortByLengthNoWords()
    {
        ArrayList<String> s1 = new ArrayList<String>(Arrays.asList());
        Main.sortByLength(s1);
        ArrayList<String> s2 = new ArrayList<String>(Arrays.asList());
        Assert.assertEquals(s1, s2);
    }
}