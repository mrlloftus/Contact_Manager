package com.loftus.contacts.data.xml;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.loftus.contacts.Contact;
import com.loftus.contacts.data.xml.ContactList;

public class JAXBXmlPort implements XmlPort {

	private String packagePath = "com.loftus.contacts.data.xml";
	private Unmarshaller unmarshaller;
	private Marshaller marshaller;
	private JAXBElement<ContactList> contactStore;
	private JAXBContext jaxbContext;
	
	public JAXBXmlPort() {
		
		try {
			jaxbContext = JAXBContext.newInstance(packagePath);
		} catch (JAXBException e) {
			System.out.println("Problem creating JAXBContext: " + e.getMessage());
			e.printStackTrace();
		}
 
	}
	
	@Override
	public void saveToDisk(List<Contact> list, String filepath) {
		ContactList contactList = new ContactList();
		contactList.setContacts(list);
		JAXBElement<ContactList> wrappedList = ObjectFactory.createContactStore(contactList);
		try {
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        	marshaller.marshal(wrappedList, new File(filepath));
		} catch (JAXBException e) {
			System.out.println("Problem marshalling xml file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> getContactsFromFile(String filepath) {
		try {
			unmarshaller = jaxbContext.createUnmarshaller();
			contactStore = (JAXBElement<ContactList>)unmarshaller.unmarshal(new File(filepath));
		} catch (JAXBException e) {
			System.out.println("Problem unmarshalling xml file: " + e.getMessage());
			e.printStackTrace();
		}
		ContactList list = contactStore.getValue();
		return list.getContacts();
	}

}
