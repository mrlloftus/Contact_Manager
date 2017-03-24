package com.loftus.contacts.tests;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.loftus.contacts.Contact;
import com.loftus.contacts.ContactManager;
import com.loftus.contacts.ContactSearchListener;
import com.loftus.contacts.data.PersistenceFactory;

public class TestContactManager {

	private ContactManager mgr;
	private PersistenceFactory readOnlyFactory;
	
	
	@Before
	public void setUp() throws Exception {
		readOnlyFactory = new ReadOnlyPersistenceFactoryForTesting();
		mgr = new ContactManager(readOnlyFactory);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSearchByLastname() {
		List<Contact> list = mgr.findContacts("Loftus");
		assertEquals("Count should be 1", 1, list.size());
		assertEquals("Firstname should be Thom", "Thom", list.get(0).getFirstname());
		
		list = mgr.findContacts("Nolan");
		assertEquals("Count should be 2", 2, list.size());
	}
	
	@Test
	public void testSearchByHashtag() {
		List<Contact> list = mgr.findContacts("Loftus");
		assertEquals("Count should be 1", 1, list.size());
		assertEquals("Firstname should be Thom", "Thom", list.get(0).getFirstname());
		
		list = mgr.findContacts("#family");
		assertEquals("Count should be 2", 2, list.size());
		
		list = mgr.findContacts("#fam");
		assertEquals("Count should be 0", 0, list.size());
	}
	
	@Test
	public void testSearchByFirstAndLastName() {
		List<Contact> list = mgr.findContacts("Lori Nolan");
		assertEquals("Count should be 1", 1, list.size());
		assertEquals("Firstname should be Lori", "Lori", list.get(0).getFirstname());
	}
	
	@Test
	public void testSearchListenerNotification() {
		List<Contact> expectedParam = mgr.getContactByLastName("Loftus");
		Mockery context = new JUnit4Mockery();
		ContactSearchListener listener = context.mock(ContactSearchListener.class, "listener");
		context.checking(new Expectations() {{
			oneOf(listener).onSearchReturn(with(expectedParam));;
		}});
		
		mgr.registerSearchListener(listener);
		mgr.getContactByLastName("Loftus");	
		
		context.assertIsSatisfied();
	}

}
