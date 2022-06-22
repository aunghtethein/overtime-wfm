package com.ot.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.ot.model.Privilege;

public class RoleBean {
	private int id;
	
	@NotEmpty(message = "{role.name.validator.msg}")
	@Pattern(regexp ="[a-zA-Z][a-zA-Z0-9- ]{2,45}", message ="{role.name.pattern.msg}")
	private String name;
	
	@NotEmpty(message = "{role.privileges.validator.msg}")
	private Set<Privilege> privileges;
	
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
	
	
}
