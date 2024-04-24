/**
 * 
 */
package com.contact.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.contact.dao.ContactRepository;
import com.contact.dao.UserRepository;
import com.contact.entities.Contact;
import com.contact.entities.User;

/**
 * @author Junaid.Khan
 *
 */

@RestController
public class SearchController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searchHandler(@PathVariable("query") String query,Principal principal) {
		//System.out.println("query "+query);
		
		User u=this.userRepository.getUserByUserName(principal.getName());
		//List<Contact> contacts=contactRepository.findByNameContainingAndUser(query, u);
		List<Contact> contacts=contactRepository.findContactsByUserId(u.getUid());
		return ResponseEntity.ok(contacts);
	}
}
