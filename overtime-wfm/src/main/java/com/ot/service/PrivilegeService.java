package com.ot.service;

import java.util.List;

import com.ot.model.Privilege;
public interface PrivilegeService {
	void save(Privilege privilege);
	void delete(Privilege privilege);
	public List<Privilege> findAll();
	Privilege findById(int id);
}
