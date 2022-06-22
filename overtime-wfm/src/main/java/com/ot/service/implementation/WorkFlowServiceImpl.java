package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Workflow;
import com.ot.repository.WorkFlowRepo;
import com.ot.service.WorkflowService;
@Service
public class WorkFlowServiceImpl implements WorkflowService {
	@Autowired
	private WorkFlowRepo repo;
	
	@Override
	public void save(Workflow workflow) {
		repo.save(workflow);
	}

	@Override
	public Workflow findById(Integer id) {
		
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID "+id));
	}

	@Override
	public List<Workflow> getAll() {
		
		return repo.findAll();
	}

	

}
