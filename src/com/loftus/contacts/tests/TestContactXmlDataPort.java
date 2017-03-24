package com.loftus.contacts.tests;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.loftus.contacts.Contact;
import com.loftus.contacts.data.xml.ContactXmlDataPort;

public class TestContactXmlDataPort {
	
	private ContactXmlDataPort port;
	private String readWriteFilepath = "C:\\Java Projects\\workspace\\ContactManager\\src\\com\\loftus\\contacts\\tests\\ReadWriteContactStore.xml";
	private String readOnlyFilepath = "C:\\Java Projects\\workspace\\ContactManager\\src\\com\\loftus\\contacts\\tests\\ReadOnlyContactStore.xml";
	
	@Before
	public void setUp() throws Exception {
		port = new ContactXmlDataPort(readOnlyFilepath);
	}
	
	@Test
	public void testGetContactById() {
		List<Contact> contact = port.getContactById(0);
		assertEquals("Get by ID: Lastname", "Lauria", contact.get(0).getLastname());
	}
	
	@Test
	public void testGetAllContacts() {
		List<Contact> contacts = port.getAllContacts();
		assertEquals("Count for all should be 5", 5, contacts.size());
		assertEquals("GetAll lastname", "Lauria", contacts.get(0).getLastname());
		assertEquals("GetAll lastname", "Nolan", contacts.get(1).getLastname());
	}

	@Test
	public void testGetSingleContactByLastName() {
		List<Contact> contacts = port.getContactByLastName("Lauria");
		assertTrue("Contact by Lastname size should be 1", contacts.size() == 1);
		Contact ctc = contacts.get(0);
		assertEquals("Lastname should be Lauria", "Lauria", ctc.getLastname());
		assertEquals("Firstname should be Diana", "Diana", ctc.getFirstname());
		}
	
	@Test
	public void testGetSingleContactByFirstName() {
		List<Contact> contacts = port.getContactByFirstName("Diana");
		assertTrue("Contact by Lastname size should be 1", contacts.size() == 1);
		Contact ctc = contacts.get(0);
		assertEquals("Lastname should be Lauria", "Lauria", ctc.getLastname());
		assertEquals("Firstname should be Diana", "Diana", ctc.getFirstname());
		}
	
	@Test
	public void testGetContactsByState() {
		List<Contact> contacts = port.getContactsByState("CA");
		assertEquals("Contact by City size should be 4", 4, contacts.size());
	}
	
	@Test
	public void testAddContactThenDelete() {
		ContactXmlDataPort port = new ContactXmlDataPort(readWriteFilepath);
		port.addContact(new ContactBuilder("Dan", "Smith").state("Ohio").build());
		assertEquals("After save list count should be 6", 6, port.getAllContacts().size());
		Contact ctc1 = port.getContactByLastName("Smith").get(0);
		assertEquals("New contact id should be 5", 5, ctc1.getId());
		port.deleteContact(ctc1);
		assertEquals("After delete list count should be 5", 5, port.getAllContacts().size());
	}
	
	@Test
	public void testUpdateContact() {
		ContactXmlDataPort port = new ContactXmlDataPort(readWriteFilepath);
		
		assertEquals("Initial list count should be 5", 5, port.getAllContacts().size());
		Contact ctc = port.getContactById(3).get(0);
		assertEquals("Name should be Thom", "Thom", ctc.getFirstname());
		ctc.setFirstname("Thom & Pam");
		port.updateContact(ctc);
		assertEquals("After update list count should still be 5", 5, port.getAllContacts().size());
		ctc = port.getContactById(3).get(0);
		assertEquals("After update name should be Thom & Pam", "Thom & Pam", ctc.getFirstname());
		ctc.setFirstname("Thom");
		port.updateContact(ctc);
		ctc = port.getContactById(3).get(0);
		assertEquals("Name should again be Thom", "Thom", ctc.getFirstname());
		assertEquals("Final list count should still be 5", 5, port.getAllContacts().size());
		
	}

}
