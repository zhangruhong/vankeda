package com.wugao.vankeda.domain.category;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import java.io.Serializable;

public class Category implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotNull
	private String id;
	@NotNull
	private String name;
	private String pid;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
