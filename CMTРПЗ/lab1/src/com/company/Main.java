package com.company;
import java.util.Scanner;

public class Main
{
    public static String[] splitter(String str)
    {
        return str.split("\\s+");
    }
    public static long countChr(String str)
    {
        return str.chars().distinct().count();
    }
    public static String minChrStr(String str)
    {
        String[] splitted = splitter(str);
        long minSize = 256;
        String minStr = "";
        for (String s: splitted)
        {
            if (countChr(s) < minSize)
            {
                minSize = countChr(s);
                minStr = s;
            }
        }
        return minStr;
    }
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System. in);
        String str = scanner. nextLine();
        String minStr = minChrStr(str);
        System.out.println(minStr);
    }
}