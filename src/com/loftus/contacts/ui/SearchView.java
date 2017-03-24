package com.loftus.contacts.ui;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTextField;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextArea;

import java.awt.Insets;

import javax.swing.JScrollPane;

import java.awt.Dimension;

@SuppressWarnings("serial")
public class SearchView extends JPanel {
	
	private JTextField searchField;
	private JTextArea searchResult;
	private JButton editBtn;
	private JButton btnSearch;
	private AddressBookFrame app;
	
	public SearchView(AddressBookFrame application) {
		this.app = application;
		
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(530, 338));
		
		JPanel searchPanel = buildSearchPanel();
		this.add(searchPanel, BorderLayout.NORTH);
	
		JScrollPane scrollPane = buildResultsPane();
		this.add(scrollPane, BorderLayout.CENTER);
		
		JPanel navPanel = buildNavPanel();
		this.add(navPanel, BorderLayout.SOUTH);
		setVisible(true);
		
	}
	
	private JPanel buildNavPanel() {
		JPanel navPanel = new JPanel();
		
		editBtn = new JButton("Edit");
		editBtn.setEnabled(false);
		editBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.setEditMode();
			}
		});
		navPanel.add(editBtn);
		
		JButton addBtn = new JButton("Add");
		addBtn.addActionListener(e -> app.setAddMode());
//		addBtn.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				app.setAddMode();
//			}
//		});
		navPanel.add(addBtn);
		
		return navPanel;		
	}

	private JScrollPane buildResultsPane() {
		searchResult = new JTextArea();
		searchResult.setMargin(new Insets(15, 15, 15, 15));
		searchResult.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					app.showPopupMenu(e.getX(), e.getY());
				}
			}               
		});
		
		JScrollPane scrollPane = new JScrollPane(searchResult, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		return scrollPane;
	}

	private JPanel buildSearchPanel() {
		JPanel searchPanel = new JPanel();
	
		searchField = new JTextField();
		searchField.setAlignmentX(Component.LEFT_ALIGNMENT);
		searchField.setColumns(30);
		searchField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					app.showPopupMenu(e.getX(), e.getY());
				}
			}               
		});
		searchPanel.add(searchField);
		
		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.search(searchField.getText());
			}
		});
		searchPanel.add(btnSearch);
				
		JButton btnShowAll = new JButton("Show All");
		btnShowAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.displayAllContacts();
			}
		});
		searchPanel.add(btnShowAll);
		
		return searchPanel;
	}
	
	public JButton getDefaultButton() {
		return this.btnSearch;
	}

	public String getSearchName() {
		return searchField.getText();
	}

	public String getHighlightedText() {
		return searchResult.getSelectedText();
	}
	
	public void displayResult(String result) {
		searchResult.setText(result);
		searchResult.setCaretPosition(0);
	}

	public void enableEditButton() {
		editBtn.setEnabled(true);		
	}

	public void disableEditButton() {
		editBtn.setEnabled(false);
	}

	public void setSearchText(String searchName) {
		searchField.setText(searchName);
	}
	
}
