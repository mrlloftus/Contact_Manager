package com.loftus.contacts.ui;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

public interface ContactsMenu {

	public abstract JMenuBar getMenuBar();
	
	public abstract JPopupMenu getPopupMenu();

	public abstract void registerNavListener(NavigationListener listener);

	public abstract void notifyNavListeners(NavEvent event);
	
	public abstract void enableEditCommand();
	
	public abstract void disableEditCommand();

}