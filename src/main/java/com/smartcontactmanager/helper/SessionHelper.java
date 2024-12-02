package com.smartcontactmanager.helper;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class SessionHelper {
    
    public void removeMessageFromSession() {
        try {
            System.out.println("removing message form session ");
            HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
            session.removeAttribute("msg");
            
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}