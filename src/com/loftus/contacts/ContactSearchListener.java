package com.loftus.contacts;

import java.util.List;

public interface ContactSearchListener {
	
	void onSearchReturn(List<Contact> result);
}
