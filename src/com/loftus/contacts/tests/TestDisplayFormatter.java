package com.loftus.contacts.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.loftus.contacts.NameFormatter;
import com.loftus.contacts.Contact;
import com.loftus.contacts.ContactFormatter;
import com.loftus.contacts.DisplayFormatter;

public class TestDisplayFormatter {

	private List<Contact> list;
	private String newLine = "\n";
	private DisplayFormatter formatter;
	
	@Before
	public void setUp() throws Exception {
		list = new ArrayList<Contact>();
		formatter = new ContactFormatter();
	}

	@Test
	public void testBasicContactFormat() {
		list.add(new ContactBuilder("John", "Smith").build());
		String expected = "John Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info";
		assertEquals("Basic format does not match", expected, formatter.format(list));
	}
	
	@Test
	public void testBasicContactWithNullFields() {
		list.add(new ContactBuilder("John", "Smith").address2(null).phone(null).notes(null).build());
		String expected = "John Smith\n123 Main St.\nTheCity, CA  95008\njsmith@gmail.com";
		assertEquals("Incorrect format for missing fields", expected, formatter.format(list));
	} 
	
	@Test
	public void testBasicContactWithEmptyFields() {
		list.add(new ContactBuilder("John", "Smith").address2("").phone("").notes("").build());
		String expected = "John Smith\n123 Main St.\nTheCity, CA  95008\njsmith@gmail.com";
		assertEquals("Incorrect format for missing fields", expected, formatter.format(list));
	} 
	
	@Test
	public void testBasicContactWithEmptyZipFields() {
		list.add(new ContactBuilder("John", "Smith").address2("").zip("").phone("").notes("").build());
		String expected = "John Smith\n123 Main St.\nTheCity, CA\njsmith@gmail.com";
		assertEquals("Incorrect format for missing zip field", expected, formatter.format(list));
	} 
	
	@Test
	public void testMultipleContactsReturned() {
		list.add(new ContactBuilder("John", "Smith").build());
		list.add(new ContactBuilder("John", "Smith").build());
		String expected = "John Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info\n\n\nJohn Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info";
		assertEquals("Multiple contact fromat incorrect", expected, formatter.format(list));
	}
	
	@Test
	public void testSupressHashtagAtStartOfNotes() {
		list.add(new ContactBuilder("John", "Smith").notes("#someTag  Some other info").build());
		String expected = "John Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info";
		assertEquals("Incorrect format for missing fields", expected, formatter.format(list));
	}

	@Test
	public void testSupressHashtagAtEndOfNotes() {
		list.add(new ContactBuilder("John", "Smith").notes("Some other info   #someTag").build());
		String expected = "John Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info";
		assertEquals("Incorrect format for missing fields", expected, formatter.format(list));
	}

	@Test
	public void testSupressHashtagInMiddleOfNotes() {
		list.add(new ContactBuilder("John", "Smith").notes("Some other #someTag info").build());
		String expected = "John Smith\n123 Main St.\nApt. 3\nTheCity, CA  95008\n(831) 123-4567\njsmith@gmail.com\n\nSome other info";
		assertEquals("Incorrect format for suppressed tags", expected, formatter.format(list));
	}

	@Test
	public void testNameAndEmailOnly() {
		list.add(new ContactBuilder("John", "Smith").address1(null).address2(null).city(null).state(null).zip(null).phone(null).notes(null).build());
		String expected = "John Smith\njsmith@gmail.com";
		assertEquals("Name/email format does not match", expected, formatter.format(list));
	}
	
	@Test
	public void testLineNamesOnly() {
		list.add(new ContactBuilder("John", "Smith").address1(null).address2(null).city(null).state(null).zip(null).phone(null).email(null).notes(null).build());
		String expected = "John Smith";
		assertEquals("First/Last format error", expected, formatter.format(list));
	}
	
	@Test
	public void testNameOnlyFormatting() {
		list.add(new ContactBuilder("George", "Jones").build());
		list.add(new ContactBuilder("Bob", "Smith").build());
		list.add(new ContactBuilder("Sam", "Shepard").build());
		String expected = "George Jones\nBob Smith\nSam Shepard";
		
		formatter = new NameFormatter();
		assertEquals("Name only format does not match", expected, formatter.format(list));
	}
	
	@Test
	public void testNameOnlyAlphaFormatting() {
		list.add(new ContactBuilder("Bob", "Barker").build());
		list.add(new ContactBuilder("Sam", "Crawford").build());
		list.add(new ContactBuilder("George", "Andrews").build());
		list.add(new ContactBuilder("Alan", "Andrews").build());
		String expected = "Alan Andrews\nGeorge Andrews\nBob Barker\nSam Crawford";
		
		formatter = new NameFormatter();
		formatter.enableAlphaSort();
		assertEquals("Alpha format does not match", expected, formatter.format(list));
		assertEquals("List should not be sorted after Alpha display", "Barker", list.get(0).getLastname());
	}
	
	@Test
	public void testLineFormatAlgorithm() {
		String expected = "John Smith" + newLine;
		String actual = formatLine(new String[] {"John", "Smith"}, new String[] {" ", newLine});
		assertEquals("First/Last format error", expected, actual);
		
		expected = "123 Main St." + newLine;
		actual = formatLine(new String[] {"123 Main St."}, new String[] {newLine});
		assertEquals("Address line format error", expected, actual);
		
		expected = "TheCity, CA  94539" + newLine;
		actual = formatLine(new String[] {"TheCity", "CA", "94539"}, new String[] {", ", "  ", newLine});
		assertEquals("City/State/Zip format error", expected, actual);
	
		expected = "TheCity, CA" + newLine;
		actual = formatLine(new String[] {"TheCity", "CA", null}, new String[] {", ", "  ", newLine});
		assertEquals("City/State format error", expected, actual);

		expected = "TheCity, 95008" + newLine;
		actual = formatLine(new String[] {"TheCity", null, "95008"}, new String[] {", ", "  ", newLine});
		assertEquals("City/Zip format error", expected, actual);

		expected = "TheCity" + newLine;
		actual = formatLine(new String[] {"TheCity", null, null}, new String[] {", ", "  ", newLine});
		assertEquals("City only format error", expected, actual);

		expected = "CA" + newLine;
		actual = formatLine(new String[] {null, "CA", null}, new String[] {", ", "  ", newLine});
		assertEquals("State only format error", expected, actual);

		expected = "95008" + newLine;
		actual = formatLine(new String[] {null, null, "95008"}, new String[] {", ", "  ", newLine});
		assertEquals("Zip only format error", expected, actual);

		expected = "";
		actual = formatLine(new String[] {null}, new String[] {newLine});
		assertEquals("Empty field line format error", expected, actual);

	}
	
	@Rule public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testLineFormatAlgorithmWithEmptySeparatorsArray() {
		thrown.expect(IllegalArgumentException.class);
		formatLine(new String[] {"anything"}, new String[0]);
	}
	
	public void testLineFormatAlgorithmWithEmptyTermsArray() {
		thrown.expect(IllegalArgumentException.class);
		formatLine(new String[0], new String[] {"anything"});
	}
	
	private String formatLine(String[] terms, String[] separators) {
		
		if(terms.length == 0 || separators.length == 0){
			throw new IllegalArgumentException("Both the terms and separators arrays must have at least one element.");
		}
		
		int lastPlacedIndex = -1;
		boolean lineContainsTerms = false;
		boolean lineShouldEndWithNewLine = separators[separators.length - 1].equals(newLine);
		String line = "";
		
		for(int i = 0; i < terms.length; i++) {
			if(termHasData(terms[i])) {
				line += terms[i] + separators[i];
				lastPlacedIndex = i;
				lineContainsTerms = true;
			}
		}
		
		if(lineContainsTerms && lineShouldEndWithNewLine && ! line.contains(newLine)) {
			line = line.replace(separators[lastPlacedIndex], separators[separators.length - 1]);
		}
		
		return line;	
	}

	private boolean termHasData(String value) {
		return !(value == null || value.equals(""));
	}
	
}

class ContactBuilder {
	private Contact ctc;
	
	public ContactBuilder(String Firstname, String Lastname) {
		ctc = new Contact();
		ctc.setFirstname(Firstname);
		ctc.setLastname(Lastname);
		ctc.setAddress("123 Main St.");
		ctc.setAddress2("Apt. 3");
		ctc.setCity("TheCity");
		ctc.setStateOfResidence("CA");
		ctc.setZip("95008");
		ctc.setPhone("(831) 123-4567");
		ctc.setEmail("jsmith@gmail.com");
		ctc.setNotes("Some other info");
	}
	
	public ContactBuilder address1(String address1) {
		ctc.setAddress(address1);
		return this;
	}

	public ContactBuilder address2(String address1) {
		ctc.setAddress2(address1);
		return this;
	}
	
	public ContactBuilder city(String city) {
		ctc.setCity(city);
		return this;
	}
	
	public ContactBuilder state(String state) {
		ctc.setStateOfResidence(state);
		return this;
	}
	
	public ContactBuilder zip(String zip) {
		ctc.setZip(zip);
		return this;
	}
	
	public ContactBuilder phone(String phone) {
		ctc.setPhone(phone);
		return this;
	}
	
	public ContactBuilder email(String email) {
		ctc.setEmail(email);
		return this;
	}
	
	public ContactBuilder notes(String notes) {
		ctc.setNotes(notes);
		return this;
	}
	
	public Contact build() {
		return ctc;
	}
}


