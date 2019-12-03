package com.company.entity;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "capacity")
    private int capacity;

    @Column (name = "date")
    private Date date;

    public Plane() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public int getCapacity() { return capacity; }

    public void setCapacity(int capacity) { this.capacity = capacity; }

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }
}
