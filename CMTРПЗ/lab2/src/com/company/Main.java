package com.company;
import com.google.gson.Gson;

public class Main
{
    public static void main(String[] args)
    {
        Person kolumbia = new Person("Kolumbia", 19, true);
        Person strawberry = new Person("Strawberry", 19, false);
        Person gloomikon = new Person("Kolumbia", 19 ,true);

        System.out.println("Kolumbia == gloomikon?");
        System.out.println(kolumbia.equals(gloomikon));
        System.out.println(kolumbia.hashCode() == gloomikon.hashCode());

        System.out.println("Kolumbia == Strawberry?");
        System.out.println(kolumbia.equals(strawberry));
        System.out.println(kolumbia.hashCode() == strawberry.hashCode());

        Gson json = new Gson();
        String tmp = json.toJson(kolumbia);
        Person kolumbia1 = json.fromJson(tmp, Person.class);

        System.out.println("Kolumbia == Kolumbia?");
        System.out.println(kolumbia.equals(kolumbia1));
        System.out.println(kolumbia.hashCode() == kolumbia1.hashCode());
    }
}