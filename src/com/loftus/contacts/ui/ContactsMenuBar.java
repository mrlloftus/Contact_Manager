package com.loftus.contacts.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ContactsMenuBar implements ContactsMenu {
	
	private JMenuBar menuBar;
	private List<NavigationListener> listeners;
	private JMenuItem mntmEdit;
	private JMenu mnFile;
	private JMenu mnEdit;
	private JPopupMenu popMenu;

	public ContactsMenuBar() {
		
		menuBar = new JMenuBar();
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyNavListeners(new NavEvent("MENU_CLOSE"));
			}
		});
		
		mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmAdd = new JMenuItem("Add Contact");
		mnEdit.add(mntmAdd);
		mntmAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				notifyNavListeners(new NavEvent("MENU_ADD"));
			}
		});
		mntmEdit = new JMenuItem("Edit Contact");
		mnEdit.add(mntmEdit);
		mntmEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e){
				notifyNavListeners(new NavEvent("MENU_EDIT"));
			}
		});
		mntmEdit.setEnabled(false);
		
		popMenu = buildContextMenu();
	}
	
	private JPopupMenu buildContextMenu() {

		JPopupMenu menu = new JPopupMenu();
		JMenuItem mntmSearch = new JMenuItem("Find");
		mntmSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyNavListeners(new NavEvent("CONTEXT_MENU_SEARCH"));
			}
		});
		menu.add(mntmSearch);
		return menu;
	}
	
	@Override
	public JMenuBar getMenuBar() {
		return this.menuBar;
	}
	
	@Override
	public JPopupMenu getPopupMenu() {
		if (popMenu != null)
			return popMenu;
		return new JPopupMenu("Null Menu");
	}

	@Override
	public void registerNavListener(NavigationListener listener) {
		if(this.listeners == null)
			this.listeners = new ArrayList<NavigationListener>();
		this.listeners.add(listener);
	}
	
	@Override
	public void notifyNavListeners(NavEvent event) {
		if(this.listeners != null) {
			for(NavigationListener listener : listeners){
				listener.NavActionPerformed(event);
			}
		}
	}

	@Override
	public void enableEditCommand() {
		mntmEdit.setEnabled(true);
	}

	@Override
	public void disableEditCommand() {
		mntmEdit.setEnabled(false);
	}

}
