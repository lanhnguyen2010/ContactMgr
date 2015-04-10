package vn.kms.launch.contactmgr.service;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.kms.launch.contactmgr.ContactMgrApp;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
public class ContactServicesTest extends TestCase {
	//Inject ComtactService
	@Autowired
	private ContactService contactService;

	/**
	 * delete a contact
	 */
	@Test
	public void deleteContact() {
		//id need test id valid 
		int contactId = 1;
		int result = contactService.deleteContacts(contactId);
		assertEquals("contactId valid ",1, result);
		//id need test is invalid not into database
		contactId = 100;
		result = contactService.deleteContacts(contactId);
		assertEquals("contactId invalid",0, result);
		//id need test is character
		String parseId = Integer.toString(contactId) ;
		assertEquals("Error", true, false);;
	}
}
