package com.ot.service;

import java.util.List;



import com.ot.model.Overtime;
import com.ot.model.Workflow;
import com.ot.model.WorkflowDto;

public interface OvertimeService {
	void save(Overtime overtime);
	void delete(Overtime overtime);
	List<Overtime> findAll();
	Overtime findById(Integer id);
	
	
	
	
}
