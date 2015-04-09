package contactmgr;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import vn.kms.launch.contactmgr.ContactMgrApp;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
public class ContactControllerTest extends TestCase {
	// @Autowired
	 // private ContactService contactService;
	 @Test
	 public void test() {
		// int result = contactService.deleteContacts(1);
		 int a=1;
		 //assertTrue("chay duoc",true);
		// assertThat(1,is(equalTo(1)));
	 }
}
