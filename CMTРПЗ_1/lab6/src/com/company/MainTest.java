package com.company;

import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class MainTest {
    Main test = new Main();

    @org.junit.Test
    public void splitter_test_two_words() {
        String[] splitted = test.splitter.splitter("Hello World");
        String[] correctSplitted = {"Hello", "World"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_three_letters() {
        String[] splitted = test.splitter.splitter("a b c");
        String[] correctSplitted = {"a", "b", "c"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_whitespaces_only() {
        String[] splitted = test.splitter.splitter("   ");
        String[] correctSplitted = {};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_words_with_many_whitespaces() {
        String[] splitted = test.splitter.splitter("Kolumbia    the    Gloom");
        String[] correctSplitted = {"Kolumbia", "the", "Gloom"};
        Assert.assertEquals(splitted, correctSplitted);
    }

    @org.junit.Test
    public void splitter_test_one_words() {
        String[] splitted = test.splitter.splitter("Strawberry");
        String[] correctSplitted = {"Strawberry"};
        Assert.assertEquals(splitted, correctSplitted);
    }


    @Test
    public void countChr_test_one_word() {
        long chars = test.charCounter.countChar("hello");
        long correctChars = 4;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_empty_line() {
        long chars = test.charCounter.countChar("");
        long correctChars = 0;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_two_chrs() {
        long chars = test.charCounter.countChar("Aa");
        long correctChars = 2;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_long_word() {
        long chars = test.charCounter.countChar("Strawberry");
        long correctChars = 8;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void countChr_test_another_long_word() {
        long chars = test.charCounter.countChar("Kolumbia");
        long correctChars = 8;
        Assert.assertEquals(chars, correctChars);
    }

    @Test
    public void minChrStr_test_two_words() {
        String minStr = test.minCharStr.findMinCharStr("hello world");
        String correctMinStr = "hello";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_four_words() {
        String minStr = test.minCharStr.findMinCharStr("My name is Kolumbia");
        String correctMinStr = "My";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_one_word() {
        String minStr = test.minCharStr.findMinCharStr("Strawberry");
        String correctMinStr = "Strawberry";
        Assert.assertEquals(minStr, correctMinStr);
    }

    @Test
    public void minChrStr_test_empty_line() {
        String minStr = test.minCharStr.findMinCharStr("");
        String correctMinStr = "";
        Assert.assertEquals(minStr, correctMinStr);
    }


    @Test
    public void minChrStr_test_five_words() {
        String minStr = test.minCharStr.findMinCharStr("12345 123456 123345 abc AAab");
        String correctMinStr = "abc";
        Assert.assertEquals(minStr, correctMinStr);
    }
}