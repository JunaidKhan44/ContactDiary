/**
 * 
 */
package com.contact.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.contact.entities.User;

/**
 * @author Junaid.Khan
 *
 */
public interface UserRepository extends JpaRepository<User, Integer> {

	
	@Query("select u from User u where u.email= :email")
	public User getUserByUserName(@Param("email") String email);
}
