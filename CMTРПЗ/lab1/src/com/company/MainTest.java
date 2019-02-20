package com.company;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void splitter_test_1() {
        String[] splitted = Main.splitter("Hello World");
        String[] correctSplitted = {"Hello", "World"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_2() {
        String[] splitted = Main.splitter("a b c");
        String[] correctSplitted = {"a", "b", "c"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_3() {
        String[] splitted = Main.splitter("   ");
        String[] correctSplitted = {};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_4() {
        String[] splitted = Main.splitter("Kolumbia    the    Gloom");
        String[] correctSplitted = {"Kolumbia", "the", "Gloom"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_5() {
        String[] splitted = Main.splitter("Strawberry");
        String[] correctSplitted = {"Strawberry"};
        Assert.assertEquals(splitted, correctSplitted);
    }


    @Test
    public void countChr_test_1() {
        long chars = Main.countChr("hello");
        long correctChars = 4;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_2() {
        long chars = Main.countChr("");
        long correctChars = 0;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_3() {
        long chars = Main.countChr("Aa");
        long correctChars = 2;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_4() {
        long chars = Main.countChr("Strawberry");
        long correctChars = 8;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_5() {
        long chars = Main.countChr("Kolumbia");
        long correctChars = 8;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void minChrStr_test_1() {
        String minStr = Main.minChrStr("hello world");
        String correctMinStr = "hello";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_2() {
        String minStr = Main.minChrStr("My name is Kolumbia");
        String correctMinStr = "My";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_3() {
        String minStr = Main.minChrStr("Strawberry");
        String correctMinStr = "Strawberry";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_4() {
        String minStr = Main.minChrStr("");
        String correctMinStr = "";
        Assert.assertEquals(minStr, correctMinStr);
    }


    @Test
    public void minChrStr_test_5() {
        String minStr = Main.minChrStr("12345 123456 123345 abc AAab");
        String correctMinStr = "abc";
        Assert.assertEquals(minStr, correctMinStr);
    }
}