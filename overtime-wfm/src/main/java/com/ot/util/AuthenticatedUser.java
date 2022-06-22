package com.ot.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.ot.model.Staff;
import com.ot.service.StaffService;
@Component
public class AuthenticatedUser {
	@Autowired
	private StaffService staffService;
	
	
	public Staff getAuthenticatedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Staff staff = staffService.findByStaffId(username);
			return staff;
		}else {
			return null;
		}
	}
	
}
