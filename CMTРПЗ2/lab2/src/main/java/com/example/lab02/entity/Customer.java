package com.example.lab02.entity;

public class Customer {
	private String id;
	private String name;
	private String surname;
	private String passport;

	public Customer() {
	}

	public String getId() { return id; }

	public void setId(String id) { this.id = id; }

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