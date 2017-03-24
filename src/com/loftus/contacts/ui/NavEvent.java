package com.loftus.contacts.ui;

public class NavEvent {
	
	private String action;
	private Object actionObject;

	public NavEvent(String actionPerformed, Object actionObject) {
		this.action = actionPerformed;
		this.actionObject = actionObject;
	}
	
	public NavEvent(String actionPerformed){
		this.action = actionPerformed;
		this.actionObject = null;
	}
	
	public String getAction() {
		return this.action;
	}
	
	public Object getActionObject() {
		return this.actionObject;
	}

}
