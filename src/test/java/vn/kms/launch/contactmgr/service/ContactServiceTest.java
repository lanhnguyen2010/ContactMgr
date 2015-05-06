package vn.kms.launch.contactmgr.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.infrastructure.AuthenticationWithToken;
import vn.kms.launch.contactmgr.util.AuthorizationException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class ContactServiceTest {

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserRepository userRepo;

    /**
     * Test getContact function
     * @throws AuthorizationException 
     */
    @Test
    public void testGetContact(){
        // test admin can get any existing contact
        User admin = userRepo.findOne(1);
        authenticate(admin, "Password1@");
        
        Contact contact1 = null;
        Contact contact2 = null;
        Contact contact3 = null;
        try {
            contact1 = contactService.getContact(1);
            contact2 = contactService.getContact(2);
            contact3 = contactService.getContact(3);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        assertNotNull(contact1);
        assertEquals("first_name1", contact1.getFirstName());
        
        assertNotNull(contact2);
        assertEquals("first_name2", contact2.getFirstName());
        
        assertNotNull(contact3);
        assertEquals("first_name3", contact3.getFirstName());

        // Id invalid
        try {
            contact1 = contactService.getContact(-1);
        } catch (AuthorizationException e1) {
            e1.printStackTrace();
        }
        assertNull(contact1);

        // The contact doesn't exist
        try {
            contact1 = contactService.getContact(9999);
        } catch (AuthorizationException e1) {
            e1.printStackTrace();
        }
        assertNull(contact1);
        
        // Test designer can get contact has company assigned by him
        User designer = userRepo.findOne(2);
        authenticate(designer, "Password1@");
        
        try {
            contact1 = contactService.getContact(1);
            contact2 = contactService.getContact(2);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        assertNotNull(contact1);
        assertEquals("first_name1", contact1.getFirstName());
        
        assertNotNull(contact2);
        assertEquals("first_name2", contact2.getFirstName());
        
        // Test designer can not get contact that does not have company assigned by him
        try {
            contact3 = contactService.getContact(3);
            fail("getContact don't throw exception when I expected it do");
        } catch (AuthorizationException e) {
        }
        
        // Test editor can get contact has company assigned by him
        User editor = userRepo.findOne(3);
        authenticate(editor, "Password1@");
        
        try {
            contact1 = contactService.getContact(1);
            contact2 = contactService.getContact(2);
        } catch (AuthorizationException e) {
            e.printStackTrace();
        }
        assertNotNull(contact1);
        assertEquals("first_name1", contact1.getFirstName());
        
        assertNotNull(contact2);
        assertEquals("first_name2", contact2.getFirstName());
        
        // Test editor can not get contact that does not have company assigned by him
        try {
            contact3 = contactService.getContact(3);
            fail("getContact don't throw exception when I expected it do");
        } catch (AuthorizationException e) {
        }
        
       
    }
    
    private void authenticate(User user, String password){
        Authentication authen = new AuthenticationWithToken(user, password);
        authen.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authen);
    }
}
