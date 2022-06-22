
package com.ot.dto;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.ot.model.Position;
import com.ot.model.Project;
import com.ot.model.Role;
import com.ot.model.Team;

public class StaffBean {
	private int id;
	
	@NotEmpty(message = "{staff.staffId.validator.msg}")
	@Pattern(regexp="2[5-6]-[0-9]{5}$",message="{staff.staffId.pattern.msg}")
	private String staffId;

	@NotEmpty(message = "{staff.name.validator.msg}")
	@Pattern(regexp ="[a-zA-Z][a-zA-Z0-9- ]{2,45}", message ="{staff.name.pattern.msg}")
	private String name;

	@NotNull(message = "{staff.salary.validator.msg}")
	private Double salary;

	@NotEmpty(message = "{staff.password.validator.msg}")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z]).{7,}$" ,message ="{staff.password.pattern.msg}")
	private String password;

	@NotNull(message = "{staff.positions.validator.msg}")
	private Position positions;

	@NotEmpty(message = "{staff.projects.validator.msg}")
	private List<Project> projects;

	@NotEmpty(message = "{staff.teams.validator.msg}")
	private List<Team> teams;

	@NotEmpty(message = "{staff.roles.validator.msg}")
	private Set<Role> roles;

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

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Position getPositions() {
		return positions;
	}

	public void setPositions(Position positions) {
		this.positions = positions;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
