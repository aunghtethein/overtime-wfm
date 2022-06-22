package com.ot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ot.model.Workflow;

@Repository
public interface WorkFlowRepo extends JpaRepository<Workflow ,Integer>{

	@Query("select w from Workflow w where w.otStatus = 2 and w.receiver = :id ")
	List<Workflow> findWorkflowByPending(String id);
	
	@Query("select w from Workflow w join w.overtime o where o.id = :id ")
	Workflow findWorkflowByOvertimeId(int id);

	@Query("select w from Workflow w where w.otStatus = 5 and w.receiver = :id ")
	List<Workflow> findWorkflowByRevise(String id);
	
}
