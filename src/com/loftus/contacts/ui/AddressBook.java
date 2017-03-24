package com.loftus.contacts.ui;

import java.awt.EventQueue;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.loftus.contacts.ContactManager;
import com.loftus.contacts.data.xml.XmlContactsMenuBar;
import com.loftus.contacts.data.xml.XmlPersistenceFactory;
import com.loftus.contacts.data.PersistenceFactory;

public class AddressBook {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Properties appProperties = loadProperties();
					String xmlFilePath = appProperties.getProperty("xmlFilePath");
					
					PersistenceFactory factory = new XmlPersistenceFactory(xmlFilePath);
					ContactManager contactManager = new ContactManager(factory);
					
					ContactsMenu menuBar = new ContactsMenuBar();
					menuBar = new XmlContactsMenuBar(menuBar, contactManager, xmlFilePath);
					
					AddressBookFrame frame = new AddressBookFrame(contactManager, menuBar);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			private Properties loadProperties() {
				Properties appProperties = new Properties();
				FileInputStream in = null;
				try {
					in = new FileInputStream("contacts.properties");
					appProperties.load(in);
				} catch (IOException e) {
					e.printStackTrace();
					System.exit(1);
				}
				finally {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				return appProperties;
			}
		});		
	}

	

}
