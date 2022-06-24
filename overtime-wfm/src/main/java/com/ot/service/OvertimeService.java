package com.ot.service;

import java.time.LocalDate;
import java.util.List;
import com.ot.model.Overtime;

public interface OvertimeService {
	void save(Overtime overtime);
	void delete(Overtime overtime);
	List<Overtime> findAll();
	Overtime findById(Integer id);
	List<Overtime> listAll(LocalDate startDate, LocalDate endDate);
	
	
	
}
