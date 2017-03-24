package com.loftus.contacts.ui;

import java.awt.CardLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.loftus.contacts.NameFormatter;
import com.loftus.contacts.Contact;
import com.loftus.contacts.ContactFormatter;
import com.loftus.contacts.ContactManager;
import com.loftus.contacts.ContactSearchListener;
import com.loftus.contacts.DisplayFormatter;
import com.loftus.contacts.data.PersistenceFactory;

@SuppressWarnings("serial")
public class AddressBookFrame extends JFrame implements ContactSearchListener, NavigationListener {

	private JPanel contentPane;
	private ContactManager manager;
	private EditView editPanel;
	private SearchView searchPanel;
	private ContactsMenu menu;
	private Contact contact;
	private DisplayFormatter formatter;
	
	public AddressBookFrame(ContactManager contactManager, ContactsMenu menuBar) {
		this.manager = contactManager;
		this.manager.registerSearchListener(this);
		this.menu = menuBar;
		
		if (menuBar != null) {
			setJMenuBar(menuBar.getMenuBar());
			menuBar.registerNavListener(this);
		}
		
		setBounds(100, 100, 450, 300);
		setTitle("My Address Book");
		addWindowListener(new WindowCloseHandler());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel(new CardLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		searchPanel = new SearchView(this);
		searchPanel.setName("searchPanel");
		this.getRootPane().setDefaultButton(searchPanel.getDefaultButton());
		
		editPanel = new EditView(this);
		editPanel.setName("editPanel");
		
		contentPane.add(searchPanel, "searchPanel");
		contentPane.add(editPanel, "editPanel");
		setContentPane(contentPane);
		this.pack();
	}

	private void closeFrame() {
		dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	class WindowCloseHandler extends WindowAdapter {
		
		@Override
		public void windowClosing(WindowEvent e) {
			manager.shutDown();
		}
	}
	
	@Override
	public void NavActionPerformed(NavEvent e) {
		
		switch(e.getAction()) {
		case "CONTEXT_MENU_SEARCH":
			formatter = new ContactFormatter();
			String searchTerm = searchPanel.getHighlightedText();
			search(searchTerm);
			break;
		case "MENU_EDIT":
			setEditMode();
			break;
		case "MENU_ADD":
			setAddMode();
			break;
		case "MENU_XML_FILE_LOAD":
			searchPanel.disableEditButton();
			menu.disableEditCommand();
			displayAllContacts();
			break;
		case "MENU_CLOSE":
			closeFrame();
			break;
		}
	}
	
	public void search(String searchTerm) {
		formatter = new ContactFormatter();
		searchPanel.setSearchText(searchTerm);
		manager.findContacts(searchTerm);
		showSearchPanel();
	}
	
	public void displayAllContacts() {
		formatter = new NameFormatter();
		formatter.enableAlphaSort();
		searchPanel.setSearchText("");
		manager.getAllContacts();
	}
	
	public void showAddedContact(Contact addedContact) {
		manager.addContact(addedContact);
		String searchText = addedContact.getFirstname() + " " + addedContact.getLastname();
		search(searchText);	
	}

	public void showEditedContact(Contact editedContact) {
		manager.updateContact(editedContact);
		String searchText = editedContact.getFirstname() + " " + editedContact.getLastname();
		search(searchText);	
	}
	
	public void showPopupMenu(int X_position, int Y_position) {
		menu.getPopupMenu().show(searchPanel, X_position, Y_position);
	}
	
	public void setAddMode() {
		editPanel.setAddMode();
		showEditPanel();
	}
	
	public void setEditMode() {
		editPanel.setContactForEdit(this.contact);
		showEditPanel();	
	}
	
	private void showEditPanel() {
		CardLayout layout = (CardLayout)contentPane.getLayout();
		layout.show(contentPane, "editPanel");
		this.getRootPane().setDefaultButton(editPanel.getDefaultButton());
		this.validate();
		this.repaint();
		this.pack();
	}

	void showSearchPanel() {
		CardLayout layout = (CardLayout)contentPane.getLayout();
		layout.show(contentPane, "searchPanel");
		this.getRootPane().setDefaultButton(searchPanel.getDefaultButton());
		this.validate();
		this.repaint();
		this.pack();
	}

	@Override
	public void onSearchReturn(List<Contact> result) {
		
		searchPanel.displayResult(formatter.format(result));
		
		if(result.size() == 1) {
			this.contact = result.get(0);
			searchPanel.enableEditButton();
			menu.enableEditCommand();
		} else {
			searchPanel.disableEditButton();
			menu.disableEditCommand();
		}
	
	}

}
