package com.ot.model;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Overtime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "FORMID")
	private String formId;
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "SUBMITTED_DATE")
	private LocalDate submittedDate;
	@Column(name = "OT_STATUS")
	private OvertimeStatus otStatus;
	@Column(name = "CURRENT_NEXT_APPROVER")
	private String currentNext;
	@Column(name = "PM_NEXT_APPROVER")
	private String pmNext;
	@Column(name = "DEPT_NEXT_APPROVER")
	private String deptNext;
	@Column(name = "DIV_NEXT_APPROVER")
	private String divNext;
	@Column(name = "HR_NEXT_APPROVER")
	private String hrNext;
	@Column(name = "WF_SENDER")
	private String wf;
	

	@OneToOne
	@JoinColumn(name = "STAFF_ID")
	private Staff staffs;

	@OneToOne
	@JoinColumn(name = "PROJECT_ID")
	private Project projects;
	
	//JoinColumn exist on reverse ownership side
	@OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "overtime_id")
	private List<OvertimeDetails> overtimeDetails = new ArrayList<>();
	
		
	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getFormId() {
		return formId;
	}



	public void setFormId(String formId) {
		this.formId = formId;
	}



	public LocalDate getSubmitted_date() {
		return submittedDate;
	}



	public void setSubmitted_date(LocalDate submitted_date) {
		this.submittedDate = submitted_date;
	}


	public OvertimeStatus getOtStatus() {
		return otStatus;
	}



	public void setOtStatus(OvertimeStatus otStatus) {
		this.otStatus = otStatus;
	}



	public String getCurrentNext() {
		return currentNext;
	}



	public void setCurrentNext(String currentNext) {
		this.currentNext = currentNext;
	}



	public String getPmNext() {
		return pmNext;
	}



	public void setPmNext(String pmNext) {
		this.pmNext = pmNext;
	}



	public String getDeptNext() {
		return deptNext;
	}



	public void setDeptNext(String deptNext) {
		this.deptNext = deptNext;
	}



	public String getDivNext() {
		return divNext;
	}



	public void setDivNext(String divNext) {
		this.divNext = divNext;
	}




	public String getHrNext() {
		return hrNext;
	}



	public void setHrNext(String hrNext) {
		this.hrNext = hrNext;
	}



	public Staff getStaffs() {
		return staffs;
	}



	public void setStaffs(Staff staffs) {
		this.staffs = staffs;
	}



	public Project getProjects() {
		return projects;
	}



	public void setProjects(Project projects) {
		this.projects = projects;
	}



	public List<OvertimeDetails> getOvertimeDetails() {
		return overtimeDetails;
	}



	public String getWf() {
		return wf;
	}



	public void setWf(String wf) {
		this.wf = wf;
	}



	public void setOvertimeDetails(List<OvertimeDetails> overtimeDetails) {
		this.overtimeDetails = overtimeDetails;
	}
	



	@Override
	public String toString() {
		return "Overtime [id=" + id + ", formId=" + formId + ", submitted_date=" + submittedDate + ", otStatus="
				+ otStatus.toString() + ", currentNext=" + currentNext + ", pmNext=" + pmNext + ", deptNext=" + deptNext
				+ ", divNext=" + divNext + ", hrNext=" + hrNext + ", staffs=" + staffs + ", projects=" + projects
				+ ", overtimeDetails=" + overtimeDetails + "]";
	}
	
	




	
	
	
	
	
	

}
