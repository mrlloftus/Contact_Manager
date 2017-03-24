package com.loftus.contacts.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestContactManager.class, TestJAXBXmlPort.class, TestContactXmlDataPort.class, TestDisplayFormatter.class })
public class AllTests {

}
