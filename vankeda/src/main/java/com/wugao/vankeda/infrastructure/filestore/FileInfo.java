package com.wugao.vankeda.infrastructure.filestore;

import java.util.Date;

public class FileInfo {

	private String name;

	private Integer size;

	private Date updateTime;
	
	private byte[] content;
	
	private boolean isDirectory;

	public FileInfo(String name, Integer size, Date updateTime) {
		this.name = name;
		this.size = size;
		this.updateTime = updateTime;
		this.isDirectory = false;
	}
	
	public FileInfo(String name, Integer size, Date updateTime, boolean isDirectory) {
		this(name, size, updateTime);
		this.isDirectory = isDirectory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

}
