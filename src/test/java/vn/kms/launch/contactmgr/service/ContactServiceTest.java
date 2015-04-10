package vn.kms.launch.contactmgr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@Profile("dev")
public class ContactServiceTest {
	@Autowired
	ContactService contactService;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetContact() {
		// Get a existing contact
		Contact contact = contactService.getContact(1);
		assertNotNull("Test contact not null if id is 1.", contact);
		assertEquals("Test contact.firstName equal Trang if id is 1", "Trang",
				contact.getFirstName());
		assertEquals("Test contact.company.code equal AB if id is 1", "AB",
				contact.getWork().getCompany().getCode());

		// Not found contact
		contact = contactService.getContact(-1);
		assertNull("Test contact null if id is -1.", contact);
	}
}