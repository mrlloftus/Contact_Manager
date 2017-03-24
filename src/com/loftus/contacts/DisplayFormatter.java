package com.loftus.contacts;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class DisplayFormatter {

	protected final String newLine = "\n";
	protected boolean alphaSort;
	
	public String format(List<Contact> contactList) {
		if(alphaSort) {
			List<Contact> sortable = new ArrayList<Contact>(contactList);
			sortable.sort(new LastNameFirstNameAlphaComparator());
			return doFormat(sortable);
		}
		return doFormat(contactList);
	};
	
	protected abstract String doFormat(List<Contact> contactList);
	
	public void enableAlphaSort() {
		this.alphaSort = true;
	}

	public void disableAlphaSort() {
		this.alphaSort = false;
	}
	
	protected String formatLine(String[] terms, String[] separators) {
		
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

	protected String removeTrailingNewLine(String result) {
		while(result.endsWith(newLine)) {
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	class LastNameFirstNameAlphaComparator implements Comparator<Contact> {

		@Override
		public int compare(Contact ct0, Contact ct1) {
			int result = ct0.getLastname().compareToIgnoreCase(ct1.getLastname());
			if (result == 0) 
				return ct0.getFirstname().compareToIgnoreCase(ct1.getFirstname());
			return result;
		}

	}
	
}