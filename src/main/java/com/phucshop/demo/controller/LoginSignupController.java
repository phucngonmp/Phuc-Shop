package com.phucshop.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.phucshop.demo.entity.User;
import com.phucshop.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginSignupController {
//	private final UserService userService;
//
//    
//    public LoginSignupController(UserService userService) {
//        this.userService = userService;
//    }
	@Autowired
	private UserService userService;
	

    
    @ModelAttribute
    public User user() {
    	return new User();
    }

   @PostMapping("/register")
    public String createUser(@ModelAttribute @Valid User user, Errors err, Model model, HttpSession session) {
	   	if(err.hasErrors()) {
	   		return "signup";
	   	}
        if(userService.findByEmail(user.getEmail()) != null){
        	model.addAttribute("emailExist", true);
        	return "signup";   	
        }
        else{
        	User u = userService.saveUser(user);
        	session.setAttribute("loggedInUser", u);
        }
        return "redirect:/";
    }
    
   @PostMapping("/login")
   public String login(@ModelAttribute User user, Model model, HttpSession session) {
	   if(!userService.checkAccount(user.getEmail(), user.getPassword())) {
		   model.addAttribute("error", true);
		   return "login";
	   }
	   
	   User u = userService.findByEmail(user.getEmail());
	   session.setAttribute("loggedInUser", u);
	   
	   

	   return u.getRole() == 1 ? "redirect:/admin/products" : "redirect:/";
   }
   
	@GetMapping("/login")
	public String showLogin() {
		return "login";
	}
	@GetMapping("/signup")
	public String showSignUp() {
		return "signup";
	}
	@GetMapping("/logout")
	public String logOut(HttpSession session) {
		session.invalidate();
		return "home-02";
	}
	
	@GetMapping("/")
	public String home() {
		return "home-02";
	}
}
