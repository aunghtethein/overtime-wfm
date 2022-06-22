package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Privilege;
import com.ot.repository.PrivilegeRepo;
import com.ot.service.PrivilegeService;
@Service
public class PrivilegeServiceImpl implements PrivilegeService{
	@Autowired
	private PrivilegeRepo privilegeRepo;
	
	@Override
	public void save(Privilege privilege) {
		privilegeRepo.save(privilege);
	}

	@Override
	public void delete(Privilege privilege) {
		privilegeRepo.delete(privilege);
	}

	@Override
	public List<Privilege> findAll() {
		return privilegeRepo.findAll();
	}

	@Override
	public Privilege findById(int id) {
		return privilegeRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID"+id));
	}

}
