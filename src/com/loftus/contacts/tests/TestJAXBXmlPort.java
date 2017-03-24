package com.loftus.contacts.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.loftus.contacts.Contact;
import com.loftus.contacts.data.xml.JAXBXmlPort;
import com.loftus.contacts.data.xml.XmlPort;

public class TestJAXBXmlPort {

	private String filepath = "myTestXmlFile.xml";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() {
		new File(filepath).delete();
	}

	@Test
	public void testWriteThenReadXmlFile() {
		XmlPort port = new JAXBXmlPort();
		
		List<Contact> list = getContactList();
		port.saveToDisk(list, filepath);
		
		assertTrue("File does not exist", new File(filepath).isFile());
		
		List<Contact> newList = port.getContactsFromFile(filepath);
		
		assertTrue("Contact list do not match", verifyMatch(list, newList));
		
		
	}

	private boolean verifyMatch(List<Contact> list, List<Contact> newList) {
		boolean result = true;
		for(int i=0;i<list.size();i++){
			Contact ctc0 = list.get(i);
			Contact ctc1 = newList.get(i);
			if (ctc0.getId() != ctc1.getId())
				result = false;
			if (! ctc0.getLastname().equals(ctc1.getLastname()))
				result = false;
		}
		return result;
	}

	private List<Contact> getContactList() {
		List<Contact> list = new ArrayList<Contact>();
		for(int i=0;i<5;i++){
			list.add(buildContact(i));
		}
		return list;
	}

	private Contact buildContact(int id) {
		Contact ctc = new Contact();
		ctc.setId(id);
		ctc.setFirstname("Firstname");
		ctc.setLastname("Lastname" + id);
		return ctc;
	}
	
}
