/**
 * 
 */
package com.contact.entities;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.aspectj.weaver.tools.Trace;
import org.springframework.cglib.beans.BeanCopier.Generator;

import ch.qos.logback.core.subst.Token.Type;

/**
 * @author Junaid.Khan
 *
 */

@Entity
@Table(name="User")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int uid;
	private String role;
	
	@NotBlank(message="Please provide Name::")
	@Size(min=8,max=30,message="Atleast 8 digit required..")
	private String name;
	private String password;
	private String cdes;
	private boolean enable;
	private String imageUrl;

	@Column(unique = true)
	private String email;
	@Column(length = 100)
	private String about;
	
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy ="user",orphanRemoval = true)
	private List<Contact>  lovContact = new ArrayList<>();

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int uid, String role, String name, String password, String cdes, boolean enable, String imageUrl,
			String email, String about, List<Contact> lovContact) {
		super();
		this.uid = uid;
		this.role = role;
		this.name = name;
		this.password = password;
		this.cdes = cdes;
		this.enable = enable;
		this.imageUrl = imageUrl;
		this.email = email;
		this.about = about;
		this.lovContact=lovContact;
	}



	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCdes() {
		return cdes;
	}

	public void setCdes(String cdes) {
		this.cdes = cdes;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public List<Contact> getLovContact() {
		return lovContact;
	}

	public void setLovContact(List<Contact> lovContact) {
		this.lovContact = lovContact;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", role=" + role + ", name=" + name + ", password=" + password + ", cdes=" + cdes
				+ ", enable=" + enable + ", imageUrl=" + imageUrl + ", email=" + email + ", about=" + about
				+ ", lovContact=" + lovContact + "]";
	}

	
}
