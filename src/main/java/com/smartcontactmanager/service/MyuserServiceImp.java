package com.smartcontactmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartcontactmanager.entities.MyUser;
import com.smartcontactmanager.userRepo.UserDao;

@Service
public class MyuserServiceImp implements MyuserService {

	@Autowired UserDao userDao;
	@Override
	public String doRegister(MyUser myUser) {
		try {
			userDao.save(myUser);
			return "User added Successfully";
			
		}catch (Exception e) {
			return "Failed to Register User";
		}
	}

}
