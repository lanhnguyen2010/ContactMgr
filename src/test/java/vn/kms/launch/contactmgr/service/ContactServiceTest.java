package vn.kms.launch.contactmgr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Contact;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    /**
     * Test getContact function
     */
    @Test
    public void testGetContact() {
        // Get an existing contact
        Contact contact = contactService.getContact(1);
        assertNotNull(contact);
        assertEquals("Trang", contact.getFirstName());
        assertEquals("AB", contact.getWork().getCompany().getCode());
        
        // Id invalid
        contact = contactService.getContact(-1);
        assertNull(contact);
        
        // The contact doesn't exist
        contact = contactService.getContact(9999);
        assertNull(contact);
    }
}
