package com.ot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ot.model.WorkflowHistory;

@Repository
public interface WorkFlowHistoryRepo extends JpaRepository<WorkflowHistory, Integer> {
	
	@Query("select wh from WorkflowHistory wh join wh.overtime o where o.id = :id")
	List<WorkflowHistory> findWorkflowHistorybyOvertimeId(int id);
	List<WorkflowHistory> findAll();
	
	@Query("select wh from WorkflowHistory wh where wh.overtimeStatus = 3 and wh.sender = :id  ")
	List<WorkflowHistory> findWorkflowByApproved(String id);
	
	@Query("select wh from WorkflowHistory wh where wh.overtimeStatus = 4 and wh.sender = :id ")
	List<WorkflowHistory> findWorkflowByReject(String id);

	@Query("select wh from WorkflowHistory wh join wh.overtime o where o.id = :id ")
	List<WorkflowHistory> findPmWfhByOvertimeId(int id);
}
