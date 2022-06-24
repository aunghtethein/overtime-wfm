package com.ot.service.implementation;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Overtime;
import com.ot.repository.OverTimeRepo;
import com.ot.service.OvertimeService;
@Service
public class OvertimeServiceImpl implements OvertimeService {
	@Autowired
	private OverTimeRepo repo;
	@Override
	public void save(Overtime overtime) {
		repo.save(overtime);
	}

	@Override
	public void delete(Overtime overtime) {
		repo.delete(overtime);
	}

	@Override
	public List<Overtime> findAll() {
		return repo.findAll();
	}

	@Override
	public Overtime findById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID" + id));
	}

	@Override
	public List<Overtime> listAll(LocalDate startDate, LocalDate endDate) {
		if(startDate != null && endDate != null) {
			return repo.findBySubmittedDateBetween(startDate, endDate);
		}
		return repo.findAll();
	}

}
