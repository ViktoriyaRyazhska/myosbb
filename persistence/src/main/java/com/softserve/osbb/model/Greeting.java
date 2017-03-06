package com.softserve.osbb.model;

import java.sql.Timestamp;

public class Greeting {

	private String content;

	private Timestamp timestamp;

	public Greeting() {

	}

	public Greeting(String content, Timestamp timestamp) {
		super();
		this.content = content;
		this.timestamp = timestamp;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
