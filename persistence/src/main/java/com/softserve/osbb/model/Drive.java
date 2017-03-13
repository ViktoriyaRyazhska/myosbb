package com.softserve.osbb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "drive")
public class Drive implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "drive_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer driveId;
	
	@Column(name = "file")
	String file;
	
	public Drive() {
	}

	public Drive(String file) {
		this.file=file;
	}
	
	public Integer getDriveId() {
		return driveId;
	}

	public void setDriveId(Integer driveId) {
		this.driveId = driveId;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
