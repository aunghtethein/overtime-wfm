package com.ot.service;

import java.util.List;

import com.ot.model.WorkflowHistory;

public interface WorkflowHistoryService {
	void save(WorkflowHistory workflowHistory);
	
	List<WorkflowHistory> findAll();
	
}
