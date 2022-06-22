package com.ot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ot.model.Overtime;
@Repository
public interface OverTimeRepo extends JpaRepository<Overtime, Integer> {

	@Query("select o from Overtime o join o.staffs s where s.id = :id")
	List<Overtime> findOvertimebyStaffId(int id);
	
	Overtime findById(int id);
	
	@Query("select o from Overtime o join o.staffs s where o.otStatus = 2 and s.staffId = :staffId ")
	List<Overtime> findPendingOvertimeByStaffId(String staffId);
	
	@Query("select o from Overtime o join o.staffs s where o.otStatus = 6 and s.staffId = :staffId ")
	List<Overtime> findApproveOvertimeByStaffId(String staffId);
	
	@Query("select o from Overtime o join o.staffs s where o.otStatus = 4 and s.staffId = :staffId ")
	List<Overtime> findRejectOvertimeByStaffId(String staffId);
	
	@Query("select o from Overtime o join o.staffs s where o.otStatus = 5 and s.staffId = :staffId ")
	List<Overtime> findReviseOvertimeByStaffId(String staffId);
	
	@Query("select o from Overtime o join o.staffs s where s.staffId = :staffId")
	List<Overtime> findStaffId(String staffId);
	
	@Query("select o from Overtime o join o.projects p join o.staffs s where p.id = :id and s.staffId=:staffId")
	List<Overtime> findProjectId(int id,String staffId); 
	
	@Query("select o from Overtime o join o.staffs s join o.projects p where o.currentNext = :staffId and p.id=:id and o.otStatus= 6")
	List<Overtime> findCurrent(int id,String staffId);
	
//	@Query("select o from Overtime o join o.projects p join o.staffs s where p.id = :id and s.staffId=:staffId and o.submitted_date =: submitted_date")
//	List<Overtime> findDailyReport(int id,String staffId,LocalDate submitted_date); 

}
