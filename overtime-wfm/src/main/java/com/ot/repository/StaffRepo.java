package com.ot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.ot.model.Staff;

@Repository
public interface StaffRepo extends CrudRepository<Staff, Integer> {

	Staff findByStaffId(String staffId);

	List<Staff> findAll();
	
	@Query(value="select count(*) from staff",nativeQuery = true)
	public int count(boolean staff);

	@Query("select s from Staff s join s.projects p where p.id = :id")
	List<Staff> findStaffByProjectId(int id);
	
	@Query("select s from Staff s join s.positions p where p.name = 'Hr Team Member'")
	Staff findHrPositon();
	
	
}
