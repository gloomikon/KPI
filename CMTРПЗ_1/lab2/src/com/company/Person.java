package com.company;


import java.util.Objects;

public class Person
{
    private final String name;
    private final int age;
    private final boolean sex;

    public Person(String name, int age, boolean sex)
    {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
    @Override
    public final boolean equals(Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof Person))
            return false;
        Person person = (Person) o;
        return (this.age == person.age &&
                this.sex == person.sex &&
                Objects.equals(this.name, person.name));
    }

    @Override
    public final int hashCode()
    {
        return Objects.hash(name, age, sex);
    }


}