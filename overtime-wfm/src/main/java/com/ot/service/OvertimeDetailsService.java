package com.ot.service;

import java.util.List;

import com.ot.model.OvertimeDetails;

public interface OvertimeDetailsService {

	void save(OvertimeDetails overtime);
	void delete(OvertimeDetails overtime);
	List<OvertimeDetails> findAll();
	OvertimeDetails findById(Integer id);
}
