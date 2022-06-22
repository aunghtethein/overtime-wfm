package com.ot.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class PositionBean {
	private int id;
	@NotEmpty(message="{position.name.validator.msg}")
	@Pattern(regexp ="[a-zA-Z][a-zA-Z0-9- ]{2,45}", message ="{position.name.pattern.msg}")
	private String name;
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
	
	
}
