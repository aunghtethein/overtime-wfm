package com.ot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ot.model.Project;

@Repository
public interface ProjectRepo extends JpaRepository<Project, Integer> {
	
	@Query("select p from Project p join p.staffs s where p.id = :id")
	List<Project> findStaffByProjectId(int id);
	
	Project findProjectById(int id);
	
	Project findByProjectId(String projectId);
	
	@Query("select p from Project p join p.staffs s where s.staffId = :staffId")
	List<Project> findProjectByStaffId(String staffId);
	
	@Query(value="select count(*) from project",nativeQuery = true)
	public int count( boolean project);
	
	@Query(value="(SELECT * FROM project ORDER BY ID DESC LIMIT 10)\r\n"
			+ "ORDER BY ID ASC;",nativeQuery = true)
	List<Project> tenProject();
	
		 
}
