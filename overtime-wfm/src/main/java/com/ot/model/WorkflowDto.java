package com.ot.model;

import java.time.LocalDate;
//This is Dto for Workflow
public class WorkflowDto {
	
	private int id;
	private String initial_requester;
	private String sender;
	private String senderName;
	private String receiver;
	private String receiverName;
	private LocalDate createdDate;
	private String remark;
	private OvertimeStatus otStatus;
	private int otId;
	
	
	private Overtime overtime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInitial_requester() {
		return initial_requester;
	}

	public void setInitial_requester(String initial_requester) {
		this.initial_requester = initial_requester;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	
	public Overtime getOvertime() {
		return overtime;
	}

	public void setOvertime(Overtime overtime) {
		this.overtime = overtime;
	}
	
	public OvertimeStatus getOtStatus() {
		return otStatus;
	}

	public void setOtStatus(OvertimeStatus otStatus) {
		this.otStatus = otStatus;
	}

	public int getOtId() {
		return otId;
	}

	public void setOtId(int otId) {
		this.otId = otId;
	}
	
	
	
	
	
	
	
	
}
