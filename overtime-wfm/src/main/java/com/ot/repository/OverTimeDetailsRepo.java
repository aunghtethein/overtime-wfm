package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ot.model.OvertimeDetails;

@Repository
public interface OverTimeDetailsRepo extends JpaRepository<OvertimeDetails, Integer> {
		
}
