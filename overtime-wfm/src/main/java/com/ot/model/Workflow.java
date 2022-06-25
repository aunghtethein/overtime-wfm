package com.ot.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Workflow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private int id;
	@Column(name="SENDER")
	private String sender;
	@Column(name="SENDERNAME")
	private String senderName;
	@Column(name="RECEIVER")
	private String receiver;
	@Column(name="RECEIVER_NAME")
	private String receiverName;
	@Column(name="CREATED_DATE")
	private LocalDate createdDate;
	@Column(name="OT_STATUS")
	private OvertimeStatus otStatus;
	@Column(name="REMARK")
	private String remark;
	
	@OneToOne
    @JoinColumn(name = "overtime_id")
	private Overtime overtime;


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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

	public OvertimeStatus getOtStatus() {
		return otStatus;
	}
	public void setOtStatus(OvertimeStatus otStatus) {
		this.otStatus = otStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public Overtime getOvertime() {
		return overtime;
	}
	public void setOvertime(Overtime overtime) {
		this.overtime = overtime;
	}
	@Override
	public String toString() {
		return "Workflow [overtime=" + overtime + "]";
	}
	
	
	
	
	
	
	
	
}
