package com.loftus.contacts.data.xml;

import java.util.List;

import com.loftus.contacts.Contact;

public interface XmlPort {
	
	void saveToDisk(List<Contact> list, String filepath);
	List<Contact> getContactsFromFile(String filepath);

}
