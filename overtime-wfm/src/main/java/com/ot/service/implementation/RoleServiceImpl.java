package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Role;
import com.ot.repository.RoleRepo;
import com.ot.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public void save(Role role) {
		roleRepo.save(role);
	}

	@Override
	public void delete(Role role) {
		roleRepo.delete(role);
	}

	@Override
	public List<Role> findAll() {
		return roleRepo.findAll();
	}

	@Override
	public Role findById(int id) {
		return roleRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID"+id));
	}

}
