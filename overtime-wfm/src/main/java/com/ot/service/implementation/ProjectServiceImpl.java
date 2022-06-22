package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Project;
import com.ot.repository.ProjectRepo;
import com.ot.service.ProjectService;
@Service
public class ProjectServiceImpl implements ProjectService{
	@Autowired
	private ProjectRepo repo;

	@Override
	public void save(Project project) {
		repo.save(project);
	}

	@Override
	public void delete(Project project) {
		repo.delete(project);
	}

	@Override
	public List<Project> findAll() {
		return repo.findAll();
	}

	@Override
	public Project findById(int id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+ id));
	}
	

}
