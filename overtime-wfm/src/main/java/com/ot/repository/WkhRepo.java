package com.ot.repository;


import java.util.Date;

import com.ot.model.WH;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WkhRepo extends JpaRepository<WH, Integer>{

	WH findByProjectId(String projectId);
	
	WH findByStaffIdAndDate(String staffId, Date date);
	
	
	
	
}
