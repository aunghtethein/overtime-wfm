package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Staff;
import com.ot.repository.StaffRepo;
import com.ot.service.StaffService;
@Service
public class StaffServiceImpl implements StaffService {
	@Autowired
	private StaffRepo repo;
	@Override
	public void save(Staff staff) {
		repo.save(staff);
	}
	@Override
	public void delete(Staff staff) {
		repo.delete(staff);
	}
	@Override
	public List<Staff> findAll() {
		return repo.findAll();
	}
	@Override
	public Staff findById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+id));

	}
	@Override
	public Staff findByStaffId(String staffId) {
		return repo.findByStaffId(staffId);
	}
	
}
