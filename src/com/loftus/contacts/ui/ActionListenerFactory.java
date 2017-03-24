package com.loftus.contacts.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.loftus.contacts.ContactManager;

public abstract class ActionListenerFactory {
	
	private static AddressBookFrame app;
	private static ContactManager manager;
	private static ContactsMenu menu;
	
	public static void Initialize(AddressBookFrame application, ContactManager manager, ContactsMenu menu) {
		ActionListenerFactory.app = application;
		ActionListenerFactory.manager = manager;
		ActionListenerFactory.menu = menu;
	}
	
	public static ActionListener getInstanceFor(String operation) {
		
		switch(operation) {
		case "ADD_BTN":
			return buildAddListener();
		case "EDIT_BTN":
			return buildEditListener();
		}
		return null;
	}

	private static ActionListener buildEditListener() {
		return null;
	}

	private static ActionListener buildAddListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.setAddMode();
//				app.showEditPanel();
			}
		};
}

}
