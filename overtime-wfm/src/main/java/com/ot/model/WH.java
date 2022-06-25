package com.ot.model;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WH {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String staffId;
	private String projectId;
	private Date date;
	private double actualHour;
	public WH() {}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getActualHour() {
		return actualHour;
	}
	public void setActualHour(double actualHour) {
		this.actualHour = actualHour;
	}
	@Override
	public String toString() {
		return "WorkingHour [id=" + id + ", staffId=" + staffId + ", projectId=" + projectId + ", date=" + date
				+ ", actualHour=" + actualHour + "]";
	}
}
