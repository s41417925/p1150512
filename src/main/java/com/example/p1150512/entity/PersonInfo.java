package com.example.p1150512.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_info")
public class PersonInfo {

	@Id
	@Column(name = "id", nullable = false)
	private String id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "age", nullable = false)
	private int age;
	
	@Column(name = "sex", nullable = false)
	private String sex;
	
	@Column(name = "city", nullable = false)
	private String city;

	public PersonInfo() {

	}

	public PersonInfo(String id, String name, int age, String sex, String city) {
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.city = city;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
