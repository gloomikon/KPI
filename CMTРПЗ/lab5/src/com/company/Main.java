package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Collections;

class LenCompare implements Comparator<String>
{
    public int compare(String o1, String o2)
    {
        return Integer.compare(o1.length(), o2.length());
    }
}

public class Main
{
    public static ArrayList<String> readFromFile(File file) throws FileNotFoundException
    {
        ArrayList<String>strings = new ArrayList<String>();
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
            strings.add(sc.nextLine());
        return strings;
    }

    public static void writeToFile(File file, ArrayList<String> strings) throws IOException
    {
        FileWriter fileWriter = new FileWriter(file);
        for (String s: strings) {
            fileWriter.write(s);
            fileWriter.write("\n");
        }
        fileWriter.close();
    }

    public static void sortByLength(ArrayList<String> strings)
    {
        Collections.sort(strings, new LenCompare());
    }

    public static void main(String[] args)
    {
        File input = new File("./src/com/company/input.txt");
        File output = new File("./src/com/company/output.txt");
        try {
            ArrayList<String> strings = readFromFile(input);
            sortByLength(strings);
            writeToFile(output, strings);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        }
        catch (IOException e)
        {
            System.out.println("Error while writing");
        }
    }
}
