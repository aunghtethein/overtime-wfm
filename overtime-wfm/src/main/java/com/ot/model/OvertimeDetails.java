package com.ot.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class OvertimeDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="START_DATE")
	@NotNull(message = "{overtimeDetails.startDate.validation.msg}")
	private LocalDate startDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="END_DATE")
	@NotNull(message = "{overtimeDetails.endDate.validation.msg}")
	private LocalDate endDate;
	@DateTimeFormat(pattern = "H:mm")
	@Column(name="START_TIME")
	@NotNull(message = "{overtimeDetails.startTime.validation.msg}")
	private LocalTime startTime;
	@DateTimeFormat(pattern = "H:mm")
	@Column(name="END_TIME")
	@NotNull(message = "{overtimeDetails.endTime.validation.msg}")
	private LocalTime endTime;
	@Column(name="REASON")
	@NotEmpty(message = "{overtimeDetails.reason.validation.msg}")
	private String reason;
	@Enumerated(EnumType.STRING)
	@Column(name="DAYTYPE")
	private DayType dayType;
	@Column(name="OTRANGE")
	private double otRange;
	@Column(name="OTTOTAL_HOURS")
	private double otTotalHour;
	@Column(name = "ACTUAL_WH")
	private double actualWh;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public DayType getDayType() {
		return dayType;
	}
	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}

	public double getOtRange() {
		return otRange;
	}
	public void setOtRange(double otRange) {
		this.otRange = otRange;
	}
	public double getOtTotalHour() {
		return otTotalHour;
	}
	public void setOtTotalHour(double otTotalHour) {
		this.otTotalHour = otTotalHour;
	}
	
	
	
	public double getActualWh() {
		return actualWh;
	}
	public void setActualWh(double actualWh) {
		this.actualWh = actualWh;
	}
	@Override
	public String toString() {
		return "OvertimeDetails [id=" + id + ", startDate=" + startDate + ", endDate=" + endDate + ", startTime="
				+ startTime + ", endTime=" + endTime + ", reason=" + reason + ", dayType=" + dayType + ", otRange="
				+ otRange + ", otTotalHour=" + otTotalHour + "]";
	}
	
	
	

}	
