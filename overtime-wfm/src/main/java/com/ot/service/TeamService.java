package com.ot.service;

import java.util.List;

import com.ot.model.Team;

public interface TeamService {

	void save(Team team);
	void delete(Team team);
	Team findById(int id);
	List<Team> findAll();
	
}
