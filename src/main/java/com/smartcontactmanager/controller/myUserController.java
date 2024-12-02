package com.smartcontactmanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smartcontactmanager.contactRepo.ContactDao;
import com.smartcontactmanager.entities.Contact;
import com.smartcontactmanager.entities.MyUser;
import com.smartcontactmanager.helper.Message;
import com.smartcontactmanager.service.ContactServiceImp;
import com.smartcontactmanager.userRepo.UserDao;

@Controller
@RequestMapping("/user")
public class myUserController {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ContactServiceImp contactServiceImp;
	@Autowired
	private ContactDao contactDao;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		MyUser user = userDao.getUserByUserName(userName);
		model.addAttribute("user", user);
	}

	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {

		return "dashboard";

	}

	@RequestMapping("/add-contact")
	public String addContact(Model model) {
		model.addAttribute("contact", new Contact());

		return "addContact";

	}

	@PostMapping("/contact_process")
	public String saveContact(@ModelAttribute("contact") Contact contact, @RequestParam("imgUrl2") MultipartFile file,
			@ModelAttribute("user") MyUser myUser, Model model, HttpSession session) throws IOException {

		try {
			if (!file.isEmpty()) {
				String imgId = String.valueOf(System.currentTimeMillis());
				File saveImg = new ClassPathResource("static/img").getFile();
				Path path = (Path) Paths.get(saveImg.getAbsolutePath() + File.separator + imgId
						+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
				CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
				Files.copy(file.getInputStream(), path, options);
				contact.setImgUrl(
						imgId + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));

			}
			Contact c1 = new Contact();
			c1.setDescription(contact.getDescription());
			c1.setcEmail(contact.getcEmail());
			c1.setImgUrl(contact.getImgUrl());
			c1.setMyUser(myUser);
			c1.setName(contact.getName());
			c1.setPhoneNumber(contact.getPhoneNumber());
			c1.setWork(contact.getWork());

//			myUser.getContact().add(contact);
//			contact.setMyUser(myUser);
			contactDao.save(c1);
			model.addAttribute("contact", new Contact());
			session.setAttribute("msg", new Message("Contact Added Successfuly", "alert-success"));
		} catch (IOException e) {
			session.setAttribute("msg", new Message("Failed !", "alert-danger"));

		}
		return "addContact";
	}

	@GetMapping("/show-contact/{page}")
	public String showContact(@ModelAttribute("user") MyUser user, Model model, @PathVariable("page") Integer page) {
		Pageable pageable = PageRequest.of(page, 5);
		Page contacts = (Page) contactDao.getContactsByUser(user.getId(), pageable);
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentpage", page);
		model.addAttribute("totalpage", contacts.getTotalPages());
		model.addAttribute("user", user);
		model.addAttribute("totalrecords", contacts.getTotalElements());

		return "showContact";
	}

	@GetMapping("/my-profile")
	public String myProfile(@ModelAttribute("user") MyUser user, Model model) {
		model.addAttribute("user", user);
		return "myprofile";
	}

	@GetMapping("delete-contact/{cId}")
	public String deleteContact(@PathVariable("cId") int cId, Model model, @ModelAttribute("user") MyUser user,
			HttpSession session) {
		Contact contact = contactDao.findById(cId).get();

		if (contact != null && user.getId() == contact.getMyUser().getId()) {
			try {
				contact.setMyUser(null);
				contactDao.deleteById(cId);
				session.setAttribute("msg", new Message("Contact Deleted Successfuly", "alert-success"));

			} catch (Exception e) {
				session.setAttribute("msg", new Message("Contact not Found", "alert-danger"));
			}

		} else {
			session.setAttribute("msg", new Message("Contact not Found", "alert-danger"));
		}
		return "redirect:/user/show-contact/0";
	}

	@PostMapping("/do_user_update")
	public String doUserUpdate(@RequestParam("imgUrl2") MultipartFile file, @ModelAttribute("user") MyUser myUser,
			Model model, HttpSession session) {
		try {
			if (!file.isEmpty()) {
				String imgId = String.valueOf(System.currentTimeMillis());
				File saveImg = new ClassPathResource("static/img").getFile();
				Path path = (Path) Paths.get(saveImg.getAbsolutePath() + File.separator + imgId
						+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
				CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
				Files.copy(file.getInputStream(), path, options);
				myUser.setImgUrl(imgId + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
				MyUser u1 = userDao.findById(myUser.getId()).get();
				u1.setImgUrl(myUser.getImgUrl());
				u1.setName(myUser.getName());
				userDao.save(u1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/user/my-profile";
	}
	
	@RequestMapping("/contact_update/{CId}")
	public String contactUpdate(@ModelAttribute("user") MyUser myUser ,@PathVariable("CId")int  CId,
			Model model, HttpSession session) {

		Contact contact = contactDao.findById(CId).get();
		if(myUser.getId()== contact.getMyUser().getId()) {
			model.addAttribute("contact", contact);
			model.addAttribute("user", myUser);
			return "updatecontactform";
		}
		
			
	
		return "redirect:/user/show-contact/0";
	}
	
	
	@PostMapping("/do_contact_update/{cId}")
	public String doContactUpdate(@RequestParam("imgUrl2") MultipartFile file,@PathVariable("cId") int cId, @ModelAttribute("user") MyUser myUser,@ModelAttribute("contact") Contact contact,
			Model model, HttpSession session) {
		try {
			if (!file.isEmpty()) {
				String imgId = String.valueOf(System.currentTimeMillis());
				File saveImg = new ClassPathResource("static/img").getFile();
				Path path = (Path) Paths.get(saveImg.getAbsolutePath() + File.separator + imgId
						+ file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
				CopyOption[] options = new CopyOption[] { StandardCopyOption.REPLACE_EXISTING };
				Files.copy(file.getInputStream(), path, options);
				contact.setImgUrl(imgId + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")));
				
			}
			
			Contact c1 = contactDao.findById(cId).get();
			c1.setcEmail(contact.getcEmail());
			c1.setDescription(contact.getDescription());
			c1.setImgUrl(contact.getImgUrl());
			c1.setName(contact.getName());
			c1.setPhoneNumber(contact.getPhoneNumber());
			c1.setWork(contact.getWork());
			contactDao.save(c1);
			
		} catch (Exception e) {
			// TODO: handle exception
		}

		return "redirect:/user/show-contact/0";
	}
}
