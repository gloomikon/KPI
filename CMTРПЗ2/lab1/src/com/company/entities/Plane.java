package com.company.entities;

public class Plane {
	private int id;
	private String name;
	private int capacity;

	public Plane(int id, String name, int capacity) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
	}

	public Plane() {
	}

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public int getCapacity() { return capacity; }

	public void setCapacity(int capacity) { this.capacity = capacity; }

	@Override
	public String toString() {
		return "Plane{" +
				"id=" + id +
				", name='" + name + '\'' +
				", capacity=" + capacity +
				'}';
	}
}
