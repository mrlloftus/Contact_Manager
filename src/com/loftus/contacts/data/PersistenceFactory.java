package com.loftus.contacts.data;

public interface PersistenceFactory {

	public ContactDataPort getDataPort() throws ContactDataPortException;

}