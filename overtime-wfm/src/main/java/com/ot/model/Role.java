package com.ot.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	@NotEmpty(message = "{role.name.validator.msg}")
	private String name;
	
	public Role() {}
	public Role(String name) {
	}
	
	@ManyToMany
	@JoinTable( name="ROLE_PRIVILEGE")
	private Set<Privilege> privileges ;
	
	@ManyToMany(mappedBy = "roles")
	private Set<Staff> staffs;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public Set<Staff> getStaffs() {
		return staffs;
	}
	public void setStaffs(Set<Staff> staffs) {
		this.staffs = staffs;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	
	
}
