package com.company.entity;
import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id")
    private Integer id;

    @Column (name = "customer_id")
    private String customer_id;

    @Column (name = "plane_id")
    private String plane_id;

    @Column (name = "place")
    private String place;

    public Ticket() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getCustomer_id() { return customer_id; }

    public void setCustomer_id(String customer_id) { this.customer_id = customer_id; }

    public String getPlane_id() { return plane_id; }

    public void setPlane_id(String plane_id) { this.plane_id = plane_id; }

    public String getPlace() { return place; }

    public void setPlace(String place) { this.place = place;  }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", customer_id=" + customer_id +
                ", plane_id=" + plane_id +
                ", place='" + place + '\'' +
                '}';
    }
}
