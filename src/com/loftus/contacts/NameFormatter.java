package com.loftus.contacts;

import java.util.List;

public class NameFormatter extends DisplayFormatter {
	
	@Override
	protected String doFormat(List<Contact> contactList) {
		StringBuilder sb = new StringBuilder();
		for (Contact ctc : contactList) {
			sb.append(formatName(ctc));
		}
		return removeTrailingNewLine(sb.toString());
	}

	protected String formatName(Contact ctc) {
		return formatLine(new String[] {ctc.getFirstname(), ctc.getLastname()}, new String[] {" ", newLine});
	}
		
}
