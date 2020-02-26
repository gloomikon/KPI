package com.company;
import java.util.Scanner;

interface  Splitter {
    String[] splitter(String str);
}

interface CharCounter {
    long countChar(String str);
}
interface MinCharStr {
    String findMinCharStr(String str);
}

public class Main
{
    Splitter splitter = (str) -> {
        return str.split(("\\s+"));
    };

    CharCounter charCounter = (str) -> {
        return str.chars().distinct().count();
    };

    MinCharStr minCharStr = (str) -> {
        String[] splitted = splitter.splitter(str);
        long minSize = 256;
        String minStr = "";
        for (String s: splitted) {
            if (charCounter.countChar(s) < minSize) {
                minSize = charCounter.countChar(s);
                minStr = s;
            }
        }
        return minStr;
    };
}