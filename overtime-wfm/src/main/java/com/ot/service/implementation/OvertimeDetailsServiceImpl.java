package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.OvertimeDetails;
import com.ot.repository.OverTimeDetailsRepo;
import com.ot.service.OvertimeDetailsService;

@Service
public class OvertimeDetailsServiceImpl implements OvertimeDetailsService{
	@Autowired
	private OverTimeDetailsRepo repo;
	@Override
	public void save(OvertimeDetails overtime) {
		repo.save(overtime);
	}

	@Override
	public void delete(OvertimeDetails overtime) {
		repo.delete(overtime);
	}

	@Override
	public List<OvertimeDetails> findAll() {
		return repo.findAll();
	}

	@Override
	public OvertimeDetails findById(Integer id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID" + id));
	}

}
