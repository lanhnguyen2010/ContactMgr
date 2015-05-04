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
import vn.kms.launch.contactmgr.util.AuthorizationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;

    /**
     * Test getContact function
     * @throws AuthorizationException 
     */
    @Test
    public void testGetContact() throws AuthorizationException {
        // Get an existing contact
        Contact contact = null;
        try {
            contact = contactService.getContact(1);
        } catch (AuthorizationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        assertNotNull(contact);
        assertEquals("first_name1", contact.getFirstName());

        // Id invalid
        contact = contactService.getContact(-1);
        assertNull(contact);

        // The contact doesn't exist
        contact = contactService.getContact(9999);
        assertNull(contact);
    }
}
