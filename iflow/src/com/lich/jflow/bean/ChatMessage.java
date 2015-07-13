package com.lich.jflow.bean;

import java.util.Date;


public class ChatMessage {
	private String name;
	private String msg;
	private Type type;
	private String time;

	public ChatMessage(){};
	
	public ChatMessage(String msg, Type type, String time) {
		super();
		this.msg = msg;
		this.type = type;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public enum Type {
		INCOMING, OUTCOMING
	}
}
