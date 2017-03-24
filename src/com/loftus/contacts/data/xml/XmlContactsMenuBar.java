package com.loftus.contacts.data.xml;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.loftus.contacts.ContactManager;
import com.loftus.contacts.data.PersistenceFactory;
import com.loftus.contacts.ui.ContactsMenu;
import com.loftus.contacts.ui.NavEvent;
import com.loftus.contacts.ui.NavigationListener;

/**
 * A decorator for the ContactsMenu which adds
 * the menu item for opening a new xml contacts file.
 * It looks for a 'File' menu to add the command, but not finding that
 * it creates its own default menu.
 */
public class XmlContactsMenuBar implements ContactsMenu {
	
	private final String DEFAULT_MENU_NAME = "Contacts File";
	private ContactsMenu contactsMenuBar;
	private String defaultFilepath;
	private ContactManager contactManager;

	public XmlContactsMenuBar(ContactsMenu menuBar, ContactManager contactManager) {
		this(menuBar, contactManager, "");
	}
	
	public XmlContactsMenuBar(ContactsMenu menuBar, ContactManager contactManager, String defaultXmlFilepath) {
		this.contactsMenuBar = menuBar;
		this.defaultFilepath = defaultXmlFilepath;
		this.contactManager = contactManager;
	}
	
	private JMenuBar buildMenuBar(JMenuBar menuBar) {
		JMenu menu;
		boolean addNewMenu = true;
		try {
			menu = findFileMenu(menuBar);
			addNewMenu = false;
		} catch (Exception e) {
			menu = new JMenu(DEFAULT_MENU_NAME);
		}
		menu.add(buildXmlMenuItem(), 0);
		if(addNewMenu)
			menuBar.add(menu);
		return menuBar;
	}

	private JMenu findFileMenu(JMenuBar menuBar) throws Exception {
		JMenu menu;
		for(Component component : menuBar.getComponents()) {
			try {
				menu = (JMenu) component;
			} catch (ClassCastException e) {
				continue;
			}
			if (menu.getText() == "File")
				return menu;
		}
		throw new Exception("File menu not found");
	}

	private JMenuItem buildXmlMenuItem() {
		final JMenuItem mntmXml = new JMenuItem("Open contacts file");
		mntmXml.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e){
				final JFileChooser fc = new JFileChooser();
				fc.setApproveButtonToolTipText("Open contacts file");
				fc.setDialogTitle("Open contacts file");
				fc.setAcceptAllFileFilterUsed(false);
				fc.setFileFilter(new FileNameExtensionFilter("xml", "xml"));
				fc.setCurrentDirectory(new File(defaultFilepath));
				int returnVal = fc.showOpenDialog(contactsMenuBar.getMenuBar());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            PersistenceFactory factory = new XmlPersistenceFactory(file.getAbsolutePath());
		            contactManager.setPersistanceFactory(factory);
		            contactsMenuBar.notifyNavListeners(new NavEvent("MENU_XML_FILE_LOAD"));
		        }
			}
		});
		return mntmXml;
	}

	@Override
	public JMenuBar getMenuBar() {
		return buildMenuBar(contactsMenuBar.getMenuBar());
	}
	
	@Override
	public JPopupMenu getPopupMenu() {
		return contactsMenuBar.getPopupMenu();
	}

	@Override
	public void registerNavListener(NavigationListener listener) {
		contactsMenuBar.registerNavListener(listener);
	}

	@Override
	public void notifyNavListeners(NavEvent event) {
		contactsMenuBar.notifyNavListeners(event);
	}

	@Override
	public void enableEditCommand() {
		contactsMenuBar.enableEditCommand();
	}

	@Override
	public void disableEditCommand() {
		contactsMenuBar.disableEditCommand();
	}

}
