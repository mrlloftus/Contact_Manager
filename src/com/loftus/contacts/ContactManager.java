package com.loftus.contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.loftus.contacts.Contact;
import com.loftus.contacts.data.*;

public class ContactManager {

	private ContactDataPort data;
	private List<ContactSearchListener> searchListeners;
	
	public ContactManager(PersistenceFactory factory) {
		setPersistanceFactory(factory);
	}

	public void setPersistanceFactory(PersistenceFactory factory) {
		try {
			data = factory.getDataPort();
		} catch (ContactDataPortException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public List<Contact> getAllContacts() {
		List<Contact> result = data.getAllContacts();
		notifyListeners(result);
		return result;
	}

	public List<Contact> getContactById(int id) {
		List<Contact> result = data.getContactById(id);
		notifyListeners(result);
		return result;
	}

	public List<Contact> getContactByLastName(String lastName) {
		List<Contact> result = data.getContactByLastName(lastName);
		notifyListeners(result);
		return result;
	}

	public List<Contact> getContactByFirstName(String firstName) {
		List<Contact> result = data.getContactByFirstName(firstName);
		notifyListeners(result);
		return result;
	}

	public List<Contact> getContactByState(String stateAbbrv) {
		List<Contact> result = data.getContactsByState(stateAbbrv);
		notifyListeners(result);
		return result;
	}

	public List<Contact> findContacts(String searchName) {

		if(searchName.matches("^#\\w+")) {
			return getContactsByHashtag(searchName);
		}
		
		String[] searchTerms = searchName.split(" ");
		int numberOfTerms = searchTerms.length;
		
		Set<Contact> searchResult = new HashSet<Contact>();
		if(numberOfTerms > 1)
			searchResult.addAll(this.getContactByFirstAndLastName(searchTerms[0], searchTerms[1]));
		
		if (searchResult.size() == 0) {
			for (int i = 0; i < searchTerms.length; i++) {
				searchResult.addAll(data.getContactByLastName(searchTerms[i]));
			}
		}
		
		List<Contact> returnResult = new ArrayList<Contact>(searchResult);
		notifyListeners(returnResult);
		return returnResult;
	}
	
	private List<Contact> getContactsByHashtag(String hashTagTerm) {
		List<Contact> allCtc = data.getAllContacts();
		List<Contact> result = new ArrayList<Contact>();
		
		String regExPattern = String.format("%s$|%s\\s+", hashTagTerm, hashTagTerm);
		Pattern pattern = Pattern.compile(regExPattern);
		
		for(Contact ctc : allCtc) {
			if(ctc.getNotes() != null) {
				Matcher matcher = pattern.matcher(ctc.getNotes());
				if (matcher.find())
					result.add(ctc);
			}
		}
		notifyListeners(result);
		return result;
	}

	private List<Contact> getContactByFirstAndLastName(String firstname, String lastname) {
		List<Contact> list = data.getContactByFirstName(firstname);
		List<Contact> result = new ArrayList<Contact>();
		for(Contact ctc : list) {
			if (ctc.getLastname().equals(lastname)) {
				result.add(ctc);
			}
		}
		return result;
	}

	public void addContact(Contact contact) {
		data.addContact(contact);
	}

	public void deleteContact(Contact contact) {
		data.deleteContact(contact);
	}
	
	public void updateContact(Contact contact) {
		data.updateContact(contact);
	}

	public void shutDown() {
		//passing null here results in the full contact list being committed to disk
		//can this be cleaned up?
		data.updateContact(null);
	}
	
	public void registerSearchListener(ContactSearchListener listener) {
		if(searchListeners == null)
			searchListeners = new ArrayList<ContactSearchListener>();
		searchListeners.add(listener);
	}
	
	private void notifyListeners(List<Contact> result) {
		
		if (searchListeners != null) {
			for (ContactSearchListener listener : searchListeners) {
				listener.onSearchReturn(result);
			}
		}
	}

}
