package vn.kms.launch.contactmgr.dao;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactRepository;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DaoTestConfig.class)
public class ContactRepositoryTest extends TestCase {
    @Autowired
    private ContactRepository contactRepo;
    
    @Test
    public void testSearchContact() {
        SearchResult<Contact> rerult;
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setPageIndex(1);
        criteria.setPageSize(5);
        int userId = 1;
        String userRole = "ADMINISTRATOR";
        criteria.setName("");
        criteria.setCompany("");
        criteria.setDepartment("");
        criteria.setJobTitle("");
        criteria.setMobile("");
        criteria.setEmail("");
        
        //search all
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(5)));
        assertThat(rerult.getTotalItems(), is(equalTo(9)));
        
        //search all page 2
        criteria.setPageIndex(2);
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(4)));
        assertThat(rerult.getTotalItems(), is(equalTo(9)));
        
        //search by Name
        criteria.setPageIndex(1);
        criteria.setName("display");
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(0)));
        assertThat(rerult.getTotalItems(), is(equalTo(0)));
        criteria.setName("");
        
        //search by Name with *
        criteria.setName("display*");
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(5)));
        assertThat(rerult.getTotalItems(), is(equalTo(9)));
        criteria.setName("");
        
        //search by Company
        criteria.setCompany("name1");
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(3)));
        assertThat(rerult.getTotalItems(), is(equalTo(3)));
        criteria.setCompany("");
        
        //search by email and name
        criteria.setName("*name*3");
        criteria.setEmail("*3");
        assertThat(rerult.getItems().size(), is(equalTo(3)));
        assertThat(rerult.getTotalItems(), is(equalTo(3)));
        criteria.setName("");
        criteria.setEmail("");
        
        //search by all criteria
        criteria.setName("*name9");
        criteria.setEmail("*3");
        criteria.setJobTitle("*3");
        criteria.setCompany("name3");
        criteria.setMobile("*3");
        rerult = contactRepo.searchByCriteria(criteria, userId, userRole);
        assertThat(rerult.getItems().size(), is(equalTo(1)));
        assertThat(rerult.getTotalItems(), is(equalTo(1)));
    }

}
