package com.ot.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class ProjectBean {
	private int id;
	
	@NotEmpty(message = "{project.name.validator.msg}")
	@Pattern(regexp ="[a-zA-Z][a-zA-Z0-9- ]{2,45}", message ="{project.name.pattern.msg}")
	private String name;
	
	@NotEmpty(message = "{project.projectId.validator.msg}")
	@Pattern(regexp="^DAT-\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])-\\d{3}-\\d{2}$",
			message = "{project.projectId.pattern.msg}") 
	private String projectId;
	
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
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
}
