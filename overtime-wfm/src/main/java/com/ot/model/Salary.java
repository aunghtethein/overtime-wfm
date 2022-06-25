package com.ot.model;
//This is Dto for salary upload excel file.
public class Salary {
	
	private int no;
	private String name;
	private String staffId;
	private String position;
	private Double basicPay;
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}

}
