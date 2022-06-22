package com.ot.service;

import java.util.List;


import com.ot.model.Staff;

public interface StaffService {
	void save(Staff staff);
	void delete(Staff staff);
	List<Staff> findAll();
	Staff findById(Integer id);
	Staff findByStaffId(String staffId);
	
	
	
	
}
