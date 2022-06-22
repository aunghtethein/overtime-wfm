package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Position;
import com.ot.repository.PositionRepo;
import com.ot.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService{
	@Autowired
	private PositionRepo repo;
	@Override
	public void save(Position position) {
		repo.save(position);
	}

	@Override
	public Position findById(int id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID"+id));

	}

	@Override
	public List<Position> findAll() {
		return repo.findAll();
	}

	@Override
	public void delete(Position position) {
		repo.delete(position);
	}
	

}
