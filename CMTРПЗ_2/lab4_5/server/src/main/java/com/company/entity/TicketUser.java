package com.company.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class TicketUser {
    @Id
    private Integer id;
    private String planeName;
    private Date date;
    private String placeName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public String toString() {
        return "TicketUser{" +
                "id=" + id +
                ", planeName='" + planeName + '\'' +
                ", date=" + date +
                ", placeName='" + placeName + '\'' +
                '}';
    }
}
