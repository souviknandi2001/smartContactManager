package com.smartcontactmanager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smartcontactmanager.entities.MyUser;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.service.MyuserServiceImp;

@Controller
public class HomeController {

	@Autowired
	MyuserServiceImp myuserServiceImp;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String index() {
		return "home";
	}

	@RequestMapping("/home")
	public String home() {
		return "home";
	}

	@RequestMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("user", new MyUser());
		return "signup";
	}
	
	@RequestMapping("/signin")
	public String signIn(Model model) {
		model.addAttribute("user", new MyUser());
		return "signin";
	}

	@RequestMapping("/do_register")
	public String doRegister(@Valid @ModelAttribute("user") MyUser myUser, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			System.out.println("souvik");
			model.addAttribute("user", myUser);
			return "signup";
		}
		myUser.setRole("ROLE_USER");
		myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
		myUser.setImgUrl("3135715.png");
		String msg = myuserServiceImp.doRegister(myUser);
		if (msg.equals("User added Successfully")) {
			session.setAttribute("msg", new Message(msg, "alert-success"));
			model.addAttribute("user", new MyUser());
			return "signup";
		} else {
			session.setAttribute("msg", new Message(msg, "alert-danger"));
			return "signup";
		}

	}
	


}
