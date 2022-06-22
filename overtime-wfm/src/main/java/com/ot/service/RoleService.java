package com.ot.service;

import java.util.List;

import com.ot.model.Role;


public interface RoleService {

	void save(Role role);
	void delete(Role role);
	public List<Role> findAll();
	
	Role findById(int id);
}
