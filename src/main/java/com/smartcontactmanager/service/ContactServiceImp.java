package com.smartcontactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.contactRepo.ContactDao;
import com.smartcontactmanager.entities.Contact;

@Service
public class ContactServiceImp implements ContactService {

	@Autowired ContactDao contactDao;
	@Override
	public String saveContact(Contact contact) {
		contactDao.save(contact);
		return null;
	}

}
