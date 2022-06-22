package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.WorkflowHistory;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.service.WorkflowHistoryService;

@Service
public class WorkflowHistoryServiceImpl implements WorkflowHistoryService {
	@Autowired
	private WorkFlowHistoryRepo repo;
	
	@Override
	public void save(WorkflowHistory workflowHistory) {
		repo.save(workflowHistory);
	}

	@Override
	public List<WorkflowHistory> findAll() {
		return repo.findAll();
	}

}
