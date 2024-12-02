package com.smartcontactmanager.userRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.entities.MyUser;

@Repository
public interface UserDao  extends JpaRepository<MyUser, Integer> {
	
	@Query("select u from MyUser u where u.email = :email")
	public MyUser getUserByUserName(@Param("email") String email);

}
