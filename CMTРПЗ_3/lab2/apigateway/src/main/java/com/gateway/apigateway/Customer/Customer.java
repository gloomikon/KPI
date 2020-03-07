package com.gateway.apigateway.Customer;

public class Customer {
    private Integer id;
    private String name;
    private String url;

    public Customer() {
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }


    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passport='" + url + '\'' +
                '}';
    }
}