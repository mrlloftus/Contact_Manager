package com.loftus.contacts.data.xml;

import com.loftus.contacts.data.*;

public class XmlPersistenceFactory implements PersistenceFactory {

	private String filepath;
	
	public XmlPersistenceFactory(String xmlFilePath) {
		this.filepath = xmlFilePath;
	}
	
	@Override
	public ContactDataPort getDataPort() throws ContactDataPortException {
		
		if(filepath == null || filepath.equals(""))
			throw new ContactDataPortException(new ContactFileNotFoundException("Xml filepath is null or empty"));
		
		return new ContactXmlDataPort(filepath);
	}
	
}
