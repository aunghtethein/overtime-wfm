package com.ot.service;

import java.util.List;

import com.ot.model.Position;

public interface PositionService  {

	void save(Position position);
	List<Position> findAll();
	Position findById(int id);
	void delete(Position position);
	
}
