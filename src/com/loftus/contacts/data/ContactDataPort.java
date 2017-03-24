package com.loftus.contacts.data;

import java.util.List;
import com.loftus.contacts.Contact;

public interface ContactDataPort {

	List<Contact> getAllContacts();

	List<Contact> getContactById(int id);

	List<Contact> getContactByLastName(String lastName);

	List<Contact> getContactByFirstName(String firstName);

	List<Contact> getContactsByState(String stateAbbrv);
	
	void addContact(Contact contact);
	
	void updateContact(Contact contact);
	
	void deleteContact(Contact contact);

}
