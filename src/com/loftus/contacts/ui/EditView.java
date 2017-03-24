package com.loftus.contacts.ui;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;

import com.loftus.contacts.Contact;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class EditView extends JPanel {

	private JTextField txtFirstname;
	private JTextField txtLastname;
	private JTextField txtAddress;
	private JTextField txtAddress2;
	private JTextField txtCity;
	private JTextField txtState;
	private JTextField txtZip;
	private JTextField txtPhone;
	private JTextField txtEmail;
	private JTextField txtId;
	private Contact contact;
	private JTextArea txtNotes;
	private FormMode formMode;
	private JButton btnOk;
	private JLabel lblEditContact;
	private AddressBookFrame app;

	public EditView(AddressBookFrame application) {
		this.app = application;
		
		setBounds(100, 100, 391, 458);  //?? needed?
		setPreferredSize(new Dimension(530, 480));
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new BorderLayout(0, 0));
		
		lblEditContact = new JLabel("Edit Contact");
		lblEditContact.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblEditContact.setFont(new Font("Tahoma", Font.BOLD, 14));
		add(lblEditContact, BorderLayout.NORTH);
		
		JPanel formPanel = new JPanel();
		add(formPanel, BorderLayout.CENTER);
		formPanel.setLayout(null);
		
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(41, 31, 72, 14);
		formPanel.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(41, 59, 72, 14);
		formPanel.add(lblLastName);
		
		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setBounds(41, 87, 72, 14);
		formPanel.add(lblAddress);
		
		JLabel lblAddress_1 = new JLabel("Address 2:");
		lblAddress_1.setBounds(41, 118, 72, 14);
		formPanel.add(lblAddress_1);
		
		JLabel lblCity = new JLabel("City:");
		lblCity.setBounds(41, 149, 72, 14);
		formPanel.add(lblCity);
		
		JLabel lblState = new JLabel("State:");
		lblState.setBounds(41, 177, 72, 14);
		formPanel.add(lblState);
		
		JLabel lblZip = new JLabel("Zip:");
		lblZip.setBounds(41, 208, 72, 14);
		formPanel.add(lblZip);
		
		JLabel lblPhone = new JLabel("Phone:");
		lblPhone.setBounds(41, 239, 72, 14);
		formPanel.add(lblPhone);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(41, 270, 72, 14);
		formPanel.add(lblEmail);
		
		JLabel lblNotes = new JLabel("Notes:");
		lblNotes.setBounds(41, 304, 72, 14);
		formPanel.add(lblNotes);
		
		txtId = new JTextField();
		txtId.setBounds(0, 1, 86, 20);
		formPanel.add(txtId);
		txtId.setVisible(false);
		txtId.setColumns(10);
		
		txtFirstname = new JTextField();
		txtFirstname.setBounds(189, 31, 135, 20);
		formPanel.add(txtFirstname);
		txtFirstname.setColumns(10);
		
		txtLastname = new JTextField();
		txtLastname.setBounds(189, 59, 135, 20);
		formPanel.add(txtLastname);
		txtLastname.setColumns(10);
		
		txtAddress = new JTextField();
		txtAddress.setBounds(189, 87, 135, 20);
		formPanel.add(txtAddress);
		txtAddress.setColumns(10);
		
		txtAddress2 = new JTextField();
		txtAddress2.setBounds(189, 118, 135, 20);
		formPanel.add(txtAddress2);
		txtAddress2.setColumns(10);
		
		txtCity = new JTextField();
		txtCity.setBounds(189, 149, 135, 20);
		formPanel.add(txtCity);
		txtCity.setColumns(10);
		
		txtState = new JTextField();
		txtState.setBounds(189, 177, 52, 20);
		formPanel.add(txtState);
		txtState.setColumns(10);
		
		txtZip = new JTextField();
		txtZip.setBounds(189, 208, 86, 20);
		formPanel.add(txtZip);
		txtZip.setColumns(10);
		
		txtPhone = new JTextField();
		txtPhone.setBounds(189, 239, 135, 20);
		formPanel.add(txtPhone);
		txtPhone.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(189, 270, 135, 20);
		formPanel.add(txtEmail);
		txtEmail.setColumns(10);
		
		txtNotes = new JTextArea();
		txtNotes.setBorder(new LineBorder(UIManager.getColor("TextField.shadow")));
		txtNotes.setBounds(189, 312, 135, 74);
		
		JScrollPane scrollPane = new JScrollPane(txtNotes, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(189, 312, 250, 74);
		formPanel.add(scrollPane);
		
		JPanel btnPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) btnPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(btnPanel, BorderLayout.SOUTH);
		
		btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateContactFields();
				if(formMode.equals(FormMode.EDIT))
					app.showEditedContact(contact);
				else if(formMode.equals(FormMode.ADD))
					app.showAddedContact(contact);
			}
		});
		btnPanel.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnPanel.add(btnCancel);
		btnCancel.setPreferredSize(new Dimension(75, 23));
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				app.showSearchPanel();
			}
		});
		
	}

	public void setContactForEdit(Contact contact) {
		this.contact = contact;
		populateFields();
		this.formMode = FormMode.EDIT;
		lblEditContact.setText(this.formMode.toString() + " Contact");
	}

	private void populateFields() {
		this.txtFirstname.setText(this.contact.getFirstname());
		this.txtLastname.setText(this.contact.getLastname());
		this.txtAddress.setText(this.contact.getAddress());
		this.txtAddress2.setText(this.contact.getAddress2());
		this.txtCity.setText(this.contact.getCity());
		this.txtState.setText(this.contact.getStateOfResidence());
		this.txtZip.setText(this.contact.getZip());
		this.txtPhone.setText(this.contact.getPhone());
		this.txtEmail.setText(this.contact.getEmail());
		this.txtNotes.setText(this.contact.getNotes());
	}

	private void updateContactFields() {
		this.contact.setFirstname(this.txtFirstname.getText());
		this.contact.setLastname(this.txtLastname.getText());
		this.contact.setAddress(this.txtAddress.getText());
		this.contact.setAddress2(this.txtAddress2.getText());
		this.contact.setCity(this.txtCity.getText());
		this.contact.setStateOfResidence(this.txtState.getText());
		this.contact.setZip(this.txtZip.getText());
		this.contact.setPhone(this.txtPhone.getText());
		this.contact.setEmail(this.txtEmail.getText());
		this.contact.setNotes(this.txtNotes.getText());
	}

	public void setAddMode() {
		this.contact = new Contact();
		clearFormFields();
		this.formMode = FormMode.ADD;
		lblEditContact.setText(this.formMode.toString() + " Contact");
	}

	private void clearFormFields() {
		this.txtFirstname.setText(null);
		this.txtLastname.setText(null);
		this.txtAddress.setText(null);
		this.txtAddress2.setText(null);
		this.txtCity.setText(null);
		this.txtState.setText(null);
		this.txtZip.setText(null);
		this.txtPhone.setText(null);
		this.txtEmail.setText(null);
		this.txtNotes.setText(null);
	}
	
	private enum FormMode {
		ADD,
		EDIT
	}

	public JButton getDefaultButton() {
		return btnOk;
	}
}
