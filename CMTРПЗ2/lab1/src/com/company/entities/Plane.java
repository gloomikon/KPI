package com.company.entities;

public class Plane {
	private String id;
	private String name;
	private int capacity;

	public Plane() {
	}

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

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
