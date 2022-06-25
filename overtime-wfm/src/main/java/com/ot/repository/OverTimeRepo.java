package com.ot.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ot.model.Overtime;

@Repository
public interface OverTimeRepo extends JpaRepository<Overtime, Integer> {

	// Report
	// WorkFlow Finish
	// overtime status 6 from wf
	// overtime status 3 from overtime
	List<Overtime> findBySubmittedDateBetween(LocalDate startDate, LocalDate endDate);

	// Chart
	@Query("select o from Overtime o join o.projects p join o.staffs s where p.id = :id and s.staffId=:staffId and o.otStatus= 3")
	List<Overtime> findApprovedProjectId(int id, String staffId);

	// Approver Report Chart
//	@Query("select o from Overtime o join o.projects p join o.staffs s where p.id = :id and o.=:staffId and o.otStatus= 3")
//	List<Overtime> findApproverProjectId(int id,String staffId);

	// Hr Report Chart
	@Query("select o from Overtime o join o.projects p where p.id = :id and o.otStatus= 3")
	List<Overtime> findAllApprovedProjectId(int id);

	// Table
	@Query("select o from Overtime o join o.staffs s where s.staffId = :staffId and o.otStatus= 3")
	List<Overtime> findOvertimeByStaffIdAndApproved(String staffId);

	// Hr table
	@Query("select o from Overtime o where o.otStatus= 3")
	List<Overtime> findAllOvertimeByApproved();

	// Approver Table
	@Query("select o from Overtime o join o.staffs s where o.otStatus = 3 and o.currentNext = :staffId ")
	List<Overtime> findApproverOvertimeByStaffId(String staffId);

	// Dashboard Card

	@Query("select o from Overtime o join o.staffs s where s.id = :id")
	List<Overtime> findOvertimebyStaffId(int id);

	Overtime findById(int id);

	@Query("select o from Overtime o join o.staffs s where o.otStatus = 2 and s.staffId = :staffId ")
	List<Overtime> findPendingOvertimeByStaffId(String staffId);

	@Query("select o from Overtime o join o.staffs s where o.otStatus = 3 and s.staffId = :staffId ")
	List<Overtime> findApproveOvertimeByStaffId(String staffId);

	@Query("select o from Overtime o join o.staffs s where o.otStatus = 4 and s.staffId = :staffId ")
	List<Overtime> findRejectOvertimeByStaffId(String staffId);

	@Query("select o from Overtime o join o.staffs s where o.otStatus = 5 and s.staffId = :staffId ")
	List<Overtime> findReviseOvertimeByStaffId(String staffId);

	@Query("select o from Overtime o join o.staffs s where s.staffId = :staffId")
	List<Overtime> findStaffId(String staffId);

	// My Ot
	@Query("select o from Overtime o join o.projects p join o.staffs s where p.id = :id and s.staffId=:staffId and o.otStatus= 3")
	List<Overtime> findProjectId(int id, String staffId);

	// manage OT
	@Query("select o from Overtime o join o.staffs s join o.projects p where o.currentNext = :staffId and p.id=:id and o.otStatus= 3")
	List<Overtime> findCurrent(int id, String staffId);
	
	
	/**/
	
	@Query("select o from Overtime o join o.overtimeDetails d where d.id = :id")
	Overtime findByDetailId(int id);

}
