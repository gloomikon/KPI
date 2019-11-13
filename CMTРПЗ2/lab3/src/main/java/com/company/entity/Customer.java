package com.company.entity;
import javax.persistence.*;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "surname")
    private String surname;

    @Column (name = "passport")
    private String passport;

    public Customer() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getSurname() { return surname; }

    public void setSurname(String surname) { this.surname = surname; }

    public String getPassport() { return passport; }

    public void setPassport(String passport) { this.passport = passport; }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", passport='" + passport + '\'' +
                '}';
    }
}
