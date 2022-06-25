package com.ot.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import com.ot.model.DayType;

public class ReportDto {

	private int id;
	private String reason;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private Double otTotalHour;
	private DayType dayType;
	private Double otRange;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Double getOtTotalHour() {
		return otTotalHour;
	}
	public void setOtTotalHour(Double otTotalHour) {
		this.otTotalHour = otTotalHour;
	}
	
	public Double getOtRange() {
		return otRange;
	}
	public void setOtRange(Double otRange) {
		this.otRange = otRange;
	}
	public DayType getDayType() {
		return dayType;
	}
	public void setDayType(DayType dayType) {
		this.dayType = dayType;
	}
	@Override
	public String toString() {
		return "ReportDto [id=" + id + ", reason=" + reason + ", name=" + name + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", startTime=" + startTime + ", endTime=" + endTime + ", otTotalHour="
				+ otTotalHour + ", dayType=" + dayType + ", otRange=" + otRange + "]";
	}
	
	
	
	
	
	
	
}
