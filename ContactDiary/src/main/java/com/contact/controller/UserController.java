/**
 * 
 */
package com.contact.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.dao.ContactRepository;
import com.contact.dao.UserRepository;
import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.helper.Message;

/**
 * @author Junaid.Khan
 *
 */

@Controller
@RequestMapping(value = "/user")
public class UserController {


	@Autowired
	private UserRepository  repository;
	
	@Autowired
	private ContactRepository  contactRepository;
	
	@GetMapping("index")
	public String dashboard(Model model,Principal principal) {
//		String username=principal.getName();
//		System.out.println("=====  "+username);
//		
//		User user=this.repository.getUserByUserName(username);
//		System.out.println(user);
//		
//		model.addAttribute("user", user);
		model.addAttribute("title","User - Dashboard");
		
		
		return "normal/user_dashboard";
	}
	
	
	//add contact form
	@GetMapping("/add-contact")
	public String  openAndAddContactForm(Model model) {
		
		model.addAttribute("title","Add - Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact_form";
	}
	
	
	//common method used for all 
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		String username=principal.getName();
		System.out.println("=====  "+username);
		
		User user=this.repository.getUserByUserName(username);
		System.out.println(user);
		
		model.addAttribute("user", user);
		
	
	}
	
	//process contact form
	
	@PostMapping("/process-contact")
	public String processContact(@ModelAttribute Contact contact, 
			@RequestParam("profileImage")  MultipartFile file,Principal principal,HttpSession httpSession) {
		
		try {
				
			String name=principal.getName();
			
			//process and uploading file
			if(file.isEmpty()) {
				System.out.println("Image file is empty");
				contact.setImage("contact.jpg");
			}else {
			
				contact.setImage(file.getOriginalFilename());
				File saveFile=new ClassPathResource("static/images").getFile();
				
				//for unique purpose append in below line in end id's
				Path p=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
				Files.copy(file.getInputStream(),p,StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Image upload successfully..");
			}
			
			User u=this.repository.getUserByUserName(name);
			contact.setUser(u);
			u.getLovContact().add(contact);

			System.out.println("Contact Obj ::"+contact);
			this.repository.save(u);
			System.out.println("Data save successfully..");
	
			httpSession.setAttribute("message", new Message("Your contact is added !! Successfully..","success"));
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error");
			httpSession.setAttribute("message", new Message("Something went wrong..","danger"));
		}
	
		
		return "normal/add_contact_form";
	}
	
	
	//show contact handler
	//per page =7[n]
	//current page = 0 [page]
	@GetMapping("/View-contacts/{page}")
	public String showContact(@PathVariable("page") Integer page,Model m,Principal principal) {
		
	
		String name=principal.getName();
		User u=this.repository.getUserByUserName(name);
		
		Pageable pageable= PageRequest.of(page,3);
		
		Page<Contact> lst=this.contactRepository.findContactByUseId(u.getUid(),pageable);
		
		m.addAttribute("contacts",lst);
		m.addAttribute("currentPage",page);
		System.out.println(page);
		
		m.addAttribute("totalPages",lst.getTotalPages());
		
		m.addAttribute("title","View - Contacts");
		
		return "normal/show_contacts";
	}
	
	@RequestMapping("/{cId}/contact")
	public String showContactDetails(@PathVariable("cId") Integer cId,Model m,Principal principal) {
		System.out.println("CId :"+cId);
		
		Optional<Contact> conOptional= this.contactRepository.findById(cId);
		Contact contact1=conOptional.get();
		
		String userName=principal.getName();
		User user=this.repository.getUserByUserName(userName);
		
		if(user.getUid()==contact1.getUser().getUid()) {
			m.addAttribute("contact", contact1);
			m.addAttribute("title", contact1.getName());
		}
		
		return "normal/contact_detail";
	}
	
	
	//delete method handler
	@GetMapping("/delete/{cId}")
	public String deleteContact(@PathVariable("cId") Integer cId,Model m,Principal principal
			,HttpSession httpSession) {
		System.out.println("CId :"+cId);
		
		Contact contact= this.contactRepository.findById(cId).get();
		
		
		String userName=principal.getName();
		User user=this.repository.getUserByUserName(userName);
		user.getLovContact().remove(contact);
		this.repository.save(user);
		
		contact.setUser(null);
		this.contactRepository.delete(contact);
		httpSession.setAttribute("message", new Message("Contact deleted successfully..","success"));
//		if(user.getUid()==contact.getUser().getUid()) {		
//		}
	
		return "redirect:/user/View-contacts/0";
	}
	
	//update handler
	@PostMapping("/update-contact/{cid}")
	public String updateHandler(@PathVariable("cid") Integer cId,Model m) {
		System.out.println("CId :"+cId);
		
		Contact contact= this.contactRepository.findById(cId).get();
		m.addAttribute("contact", contact);
		m.addAttribute("title","Update-Contact");
	
		return "normal/update_detail";
	}
	
	// update form
	@RequestMapping(value = "/process-update", method = RequestMethod.POST)
	public String updateRecord(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Model m, HttpSession session, Principal principal) {

		// Old contact detail
		Contact oldContact = this.contactRepository.findById(contact.getCid()).get();
	

		try {
			System.out.println(contact);
			if (!file.isEmpty()) {

				//delete old field
				File deleteFile = new ClassPathResource("static/images").getFile();
				File deletedfile =new File(deleteFile,oldContact.getImage());
				deletedfile.delete();
				//
				File saveFile = new ClassPathResource("static/images").getFile();
				// for unique purpose append in below line in end id's
				Path p = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), p, StandardCopyOption.REPLACE_EXISTING);
				contact.setImage(file.getOriginalFilename());
			} else {
				contact.setImage(oldContact.getImage());
			}
			
			User user = this.repository.getUserByUserName(principal.getName());
			contact.setUser(user);

			this.contactRepository.save(contact);
			session.setAttribute("message", new Message("Your Contact is updated..", "success"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		return "redirect:/user/" + contact.getCid() + "/contact";
	}
		 
	@GetMapping("/profile")
	public String viewProfile(Model m) {
		m.addAttribute("title","Profile");
		return "normal/profile";
	}
	
}
