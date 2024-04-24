/**
 * 
 */
package com.contact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.entities.Contact;
import com.contact.entities.User;


/**
 * @author Junaid.Khan
 *
 */
public interface ContactRepository  extends JpaRepository<Contact, Integer> {

		//simple
	@Query("from Contact as c where c.user.id =:userId")
	public List<Contact> findContactsByUserId(@Param("userId") int  userId);
	
	
	
	//for pagination
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findContactByUseId(@Param("userId") int  userId,Pageable pageable);
	
	//u can also use like by making our own pattern
	//search
	public List<Contact> findByNameContainingAndUser(String name,User user);
}
