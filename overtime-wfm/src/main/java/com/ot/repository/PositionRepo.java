package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ot.model.Position;
@Repository
public interface PositionRepo extends JpaRepository<Position, Integer> {
	Position findByName(String name);
	
}
