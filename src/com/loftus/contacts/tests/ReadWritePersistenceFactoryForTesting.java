package com.loftus.contacts.tests;

import com.loftus.contacts.data.ContactDataPort;
import com.loftus.contacts.data.PersistenceFactory;
import com.loftus.contacts.data.xml.ContactXmlDataPort;

public class ReadWritePersistenceFactoryForTesting implements PersistenceFactory {
	
	@Override
	public ContactDataPort getDataPort() {
		String filepath = "C:\\Java Projects\\workspace\\ContactManager\\src\\com\\loftus\\contacts\\tests\\ReadWriteContactStore.xml";
		return new ContactXmlDataPort(filepath);
	}

}
