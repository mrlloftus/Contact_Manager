package com.loftus.contacts.data;

@SuppressWarnings("serial")
public class ContactDataPortException extends Exception {
	
	public ContactDataPortException(String message){
		super(message);
	}
	
	public ContactDataPortException(Exception innerException) {
		super(innerException);
	}

}
