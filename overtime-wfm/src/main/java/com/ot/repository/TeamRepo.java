package com.ot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ot.model.Team;
@Repository
public interface TeamRepo extends JpaRepository<Team, Integer>{

	Team findByName(String name);
	
	@Query(value="select count(*) from team",nativeQuery = true)
	public int count(boolean team);
	
	@Query("select t from Team t where t.name='HR'")
	Team findByTeamHR();
}
