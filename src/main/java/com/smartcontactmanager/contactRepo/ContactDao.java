package com.smartcontactmanager.contactRepo;

import org.springframework.data.domain.Pageable ;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smartcontactmanager.entities.Contact;

@Repository
public interface ContactDao extends JpaRepository<Contact, Integer> {
	
	@Query("from Contact as c where c.myUser.id =:userId")
	public Page<Contact> getContactsByUser(@Param("userId") int userId , Pageable pageable);

}
