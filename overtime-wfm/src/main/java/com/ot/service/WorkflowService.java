package com.ot.service;

import java.util.List;

import com.ot.model.Workflow;

public interface WorkflowService {
	void save(Workflow workflow);
	Workflow findById(Integer id);
	
	List<Workflow> getAll();
	

	
}
