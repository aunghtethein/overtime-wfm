package com.ot.service;

import java.util.List;

import com.ot.model.Project;

public interface ProjectService {
	void save(Project project);
	void delete(Project project);
	public List<Project> findAll();
	
	Project findById(int id);
	
}
