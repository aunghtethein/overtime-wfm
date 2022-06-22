package com.ot.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
public class Staff implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@Column(name = "STAFFID")
	private String staffId;
	@Column(name = "NAME")
	private String name;
	@Column(name = "SALARY")
	private double salary;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "SIGNATURE")
	private String signature;

	@OneToOne
	@JoinColumn(name = "POSITION_ID")
	private Position positions;
	
	@ManyToMany
	@JoinTable( name="STAFF_ROLE")
	private Set<Role> roles ;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "STAFF_TEAM")
	private List<Team> teams;
	
	// one to may (join table) if have to use already data while set up
	@ManyToMany
	@JoinTable(name = "STAFF_PROJECT")
	private List<Project> projects;

	public Staff() {
	}

	public Staff(int id, String staffId, String name, double salary, String password) {
		super();
		this.id = id;
		this.staffId = staffId;
		this.name = name;
		this.salary = salary;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Position getPositions() {
		return positions;
	}

	public void setPositions(Position positions) {
		this.positions = positions;
	}

	

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}



	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	@Override
	public String toString() {
		return name;
	}

	


	

}