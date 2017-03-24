package com.loftus.contacts.data.xml;

import java.util.ArrayList;
import java.util.List;
import com.loftus.contacts.Contact;
import com.loftus.contacts.data.ContactDataPort;

public class ContactXmlDataPort implements ContactDataPort {

	private XmlPort port;
	private List<Contact> contactList;
	private String filepath;
	
	public ContactXmlDataPort(String filepath) {
		this.filepath = filepath;
		this.port = new JAXBXmlPort();
		contactList = port.getContactsFromFile(filepath);
	}
	
	@Override
	public List<Contact> getAllContacts() {
		return contactList;
	}

	@Override
	public List<Contact> getContactById(int id) {
		List<Contact> result = new ArrayList<Contact>();
		for(Contact ctc : contactList) {
			if (ctc.getId() == id)
				result.add(ctc);
		}
		return result;
	}

	@Override
	public List<Contact> getContactByLastName(String lastName) {
		List<Contact> result = new ArrayList<Contact>();
		for(Contact ctc : contactList) {
			if (ctc.getLastname().equals(lastName))
				result.add(ctc);
		}
		return result;
	}
	@Override
	public List<Contact> getContactByFirstName(String firstName) {
		List<Contact> result = new ArrayList<Contact>();
		for(Contact ctc : contactList) {
			if (ctc.getFirstname().equals(firstName))
				result.add(ctc);
		}
		return result;
	}

	
	@Override
	public List<Contact> getContactsByState(String stateAbbrv) {
		List<Contact> result = new ArrayList<Contact>();
		for(Contact ctc : contactList) {
			if (ctc.getStateOfResidence().equals(stateAbbrv))
				result.add(ctc);
		}
		return result;
	}

	@Override
	public void addContact(Contact contact) {
		contact.setId(getNextContactId());
		contactList.add(contact);
		commitChanges();
	}

	private int getNextContactId() {
		int id = 0;
		for(Contact ctc : contactList) {
			id = ctc.getId() > id ? ctc.getId() : id;
		}
		return id + 1;
	}

	@Override
	public void deleteContact(Contact contact) {
		if(contactList.contains(contact)) {
			contactList.remove(contact);
		}
		commitChanges();
	}

	@Override
	public void updateContact(Contact contact) {
		commitChanges();
	}
	
	private void commitChanges() {
		port.saveToDisk(contactList, filepath);
		contactList = port.getContactsFromFile(filepath);
	}

}
