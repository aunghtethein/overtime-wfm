package com.ot.repository;



import java.util.Date;
import java.util.List;
import com.ot.model.WH;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WkhRepo extends JpaRepository<WH, Integer>{

	WH findByProjectId(String projectId);
	
	WH findByStaffIdAndDate(String staffId, Date date);
	
	@Query("select wh from WH wh where wh.staffId = :staffId ")
	List<WH> findWHByStaffId(String staffId);
	
	List<WH> findByDateBetween(Date startDate, Date endDate);
	
}
