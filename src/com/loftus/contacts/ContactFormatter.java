package com.loftus.contacts;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFormatter extends DisplayFormatter {
	
	private final String CONTACT_SEPARATOR = "\n\n\n";
	
	@Override
	protected String doFormat(List<Contact> contactList) {
		StringBuilder sb = new StringBuilder();
		Boolean separatorNeeded = false;
		for (Contact ctc : contactList) {
			if(separatorNeeded)
				sb.append(CONTACT_SEPARATOR);
			sb.append(formatContact(ctc));
			separatorNeeded = true;
		}
		return sb.toString();
	}

	protected String formatContact(Contact ctc) {
	
		String[] args = buildArgsArray(ctc);
		String result = String.format("%s%s%s%s%s%s\n%s", (Object[])args );
		return removeTrailingNewLine(result);
	}

	private String[] buildArgsArray(Contact ctc) {
		String[] args = new String[] {
				formatLine(new String[] {ctc.getFirstname(), ctc.getLastname()}, new String[] {" ", newLine}),
			    formatLine(new String[] {ctc.getAddress()}, new String[] {newLine}),
			    formatLine(new String[] {ctc.getAddress2()}, new String[] {newLine}),
			    formatLine(new String[] {ctc.getCity(), ctc.getStateOfResidence(), ctc.getZip()}, new String[] {", ", "  ", newLine}),
			    formatLine(new String[] {ctc.getPhone()}, new String[] {newLine}),
			    formatLine(new String[] {ctc.getEmail()}, new String[] {newLine}),
			    formatLine(new String[] {supressHashtags(ctc.getNotes())}, new String[] {""})
	    };
		return args;
	}

	private String supressHashtags(String notes) {
		if(notes == null)
			return notes;
		
		if(notes.contains("#")) {
			//this pattern matches any hashtag term and the spaces on either side of it
			//example match: <space>#myHashTag<space>
			Pattern pattern = Pattern.compile("\\s*#\\w+\\s*");
			Matcher matcher = pattern.matcher(notes);
			return matcher.replaceAll(" ").trim();
		}
		return notes.trim();
	}

}
