package com.smartcontactmanager.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.Transient;

@Entity

public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int CId;
	private String name;
	private String cEmail;
	private String description;
	private String imgUrl;
	private String phoneNumber;
	private String work;
	@ManyToOne
	@Transient
	private  MyUser myUser;
	
	
	
	public Contact() {
		super();
	}


	public MyUser getMyUser() {
		return myUser;
		
	}
	

	public void setMyUser(MyUser myUser) {
		this.myUser = myUser;
	}

	public Contact(int cId, String name, String email, String description, String imgUrl, String phoneNumber,
			String work) {
		super();
		CId = cId;
		this.name = name;
		this.cEmail = email;
		this.description = description;
		this.imgUrl = imgUrl;
		this.phoneNumber = phoneNumber;
		this.work = work;
	}

	public int getCId() {
		return CId;
	}

	public void setCId(int cId) {
		CId = cId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getcEmail() {
		return cEmail;
	}

	public void setcEmail(String email) {
		this.cEmail = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

}
