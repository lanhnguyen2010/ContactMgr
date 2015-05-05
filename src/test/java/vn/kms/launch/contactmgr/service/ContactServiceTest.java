package vn.kms.launch.contactmgr.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.google.common.base.Optional;

import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.infrastructure.AuthenticationWithToken;
import vn.kms.launch.contactmgr.infrastructure.DomainUsernamePasswordAuthenticationProvider;
import vn.kms.launch.contactmgr.infrastructure.TokenService;
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
    public void testGetContact() throws AuthorizationException {
        // test admin can get any existing contact
        User admin = userRepo.findOne(1);
        Authentication authen = new AuthenticationWithToken(admin, "Password1@");
        authen.setAuthenticated(true);
        
        SecurityContextHolder.getContext().setAuthentication(authen);
        Contact contact = null;
        try {
            contact = contactService.getContact(1);
        } catch (AuthorizationException e) {
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
