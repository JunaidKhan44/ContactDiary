/**
 * 
 */
package com.contact.entities;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Junaid.Khan
 *
 */

@Entity
@Table(name = "Contact")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int cid;

	private String name;
	private String nickName;
	private String work;

	@Column(length = 5000)
	private String cdesc;
	private String phone;

	@Column(unique = true)
	private String email;
	
	private String image;

	@ManyToOne
	@JsonIgnore
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Contact(int cid, String name, String nickName, String work, String cdesc, String phone, String email,
			String image, User user) {
		super();
		this.cid = cid;
		this.name = name;
		this.nickName = nickName;
		this.work = work;
		this.cdesc = cdesc;
		this.phone = phone;
		this.email = email;
		this.image = image;
		this.user = user;
	}



	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getWork() {
		return work;
	}

	public void setWork(String work) {
		this.work = work;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCdesc() {
		return cdesc;
	}

	public void setCdesc(String cdesc) {
		this.cdesc = cdesc;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return this.cid==((Contact)obj).getCid();
	}
	
	
	
//
//	@Override
//	public String toString() {
//		return "Contact [cid=" + cid + ", name=" + name + ", nickName=" + nickName + ", work=" + work + ", cdesc="
//				+ cdesc + ", phone=" + phone + ", email=" + email + ", user=" + user + "]";
//	}
//
//	
}
