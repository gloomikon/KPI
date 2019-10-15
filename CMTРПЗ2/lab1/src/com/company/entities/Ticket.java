package com.company.entities;

public class Ticket {
	private int id;
	private int customer_id;
	private int plane_id;
	private String place;

	public Ticket(int id, int customer_id, int plane_id, String place) {
		this.id = id;
		this.customer_id = customer_id;
		this.plane_id = plane_id;
		this.place = place;
	}

	public Ticket() {
	}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public int getCustomer_id() { return customer_id; }

	public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

	public int getPlane_id() { return plane_id; }

	public void setPlane_id(int plane_id) { this.plane_id = plane_id; }

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
