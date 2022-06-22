package com.ot.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ot.model.Team;
import com.ot.repository.TeamRepo;
import com.ot.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService{
	@Autowired
	private TeamRepo repo;
	@Override
	public void save(Team team) {
		repo.save(team);
	}

	@Override
	public void delete(Team team) {
		repo.delete(team);
	}

	@Override
	public Team findById(int id) {
		return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("INVALID ID"+id));
	}

	@Override
	public List<Team> findAll() {
		return repo.findAll();
	}

}
