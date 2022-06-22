package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

import com.ot.model.Overtime;
import com.ot.model.OvertimeDetails;
@Repository
public interface OverTimeDetailsRepo extends JpaRepository<OvertimeDetails, Integer> {
	
	

		
}
