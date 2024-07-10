package com.buyfurn.Buyfurn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserImage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private String type;
	@Column(length = 50000000)
	private byte[] picBytes;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public byte[] getPicBytes() {
		return picBytes;
	}
	public void setPicBytes(byte[] picBytes) {
		this.picBytes = picBytes;
	}
	public UserImage(String name, String type, byte[] picBytes) {
		super();
		this.name = name;
		this.type = type;
		this.picBytes = picBytes;
	}
	public UserImage() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
