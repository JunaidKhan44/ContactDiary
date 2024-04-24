/**
 * 
 */
package com.contact.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.contact.dao.UserRepository;
import com.contact.entities.Contact;
import com.contact.entities.User;
import com.contact.helper.Message;

import net.bytebuddy.description.modifier.MethodArguments;

/**
 * @author Junaid.Khan
 *
 */

@Controller
public class HomeController implements ErrorController {

	

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository  userRepository;
	

	@GetMapping("/home")
	public String home(Model m) {

		m.addAttribute("title", "Home - Contact Diary");

		return "home";
	}

	@GetMapping("/about")
	public String about(Model m) {

		m.addAttribute("title", "About - Contact Diary");

		return "about";
	}

	@GetMapping("/signup")
	public String signUp(Model m) {

		m.addAttribute("title", "SignUp - Contact Diary");
		m.addAttribute("user", new User());
		return "signup";
	}

	
	//handler for registration
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String registrationUser(@Valid  @ModelAttribute("user") User u,BindingResult bindingResult,
			@RequestParam(value="agreement",defaultValue = "false") boolean agreement,Model m,
			HttpSession session) {

		
		try {
			if(!agreement) {
				System.out.println("error occur::");
				throw new Exception("Please check term and condition ...");
			}
			
			
			if(bindingResult.hasErrors()) {
				System.out.println("Error ---");
				m.addAttribute("user", u);
				return "signup";
			}
			
			u.setEnable(true);
			u.setRole("Role_User");
			u.setImageUrl("default.png");
			String uf=u.getPassword();
			u.setPassword(passwordEncoder.encode(u.getPassword()));
			
			System.out.println("Agreement :"+agreement);
			System.out.println("user :"+u);
			
			User res = this.userRepository.save(u);
			
			m.addAttribute("user", res);
			
			session.setAttribute("message", new Message("Successfully registered !!","alert-success"));
			
			return "signup";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			m.addAttribute("user", u);
			session.setAttribute("message", new Message("Something went wrong !!"+e.getMessage(),"alert-error"));
			return "signup";
		}
		
	}


	@GetMapping("/signin")
	public String customLogin(Model m) {
		m.addAttribute("title", "SignIn - Contact Diary");
		m.addAttribute("user", new User());
		return "login";
	}

	

}
