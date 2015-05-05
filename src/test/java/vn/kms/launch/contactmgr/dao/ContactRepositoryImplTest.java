package vn.kms.launch.contactmgr.dao;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactRepository;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DaoTestConfig.class)
public class ContactRepositoryImplTest extends TestCase {
    @Autowired
    ContactRepository contactRepo;

    @Test
    public void testGetCompanyNames() {
        // test admin can get all name of companies
        List<Itemized> companyNames = contactRepo.getCompanyNames(1);
        assertNotNull(companyNames);
        assertEquals(3, companyNames.size());
        
        // test designer can get companies is assigned
        companyNames = contactRepo.getCompanyNames(2);
        assertNotNull(companyNames);
        assertEquals(2, companyNames.size());
        assertEquals("name1", companyNames.get(0).getName());
        assertEquals("name2", companyNames.get(1).getName());
        
        // test editor can get companies is assigned
        companyNames = contactRepo.getCompanyNames(3);
        assertNotNull(companyNames);
        assertEquals(2, companyNames.size());
        assertEquals("name1", companyNames.get(0).getName());
        assertEquals("name2", companyNames.get(1).getName());
    }

    @Test
    public void testSearchByCriteria() {
        ContactSearchCriteria criteria = new ContactSearchCriteria();
        criteria.setName("display_name*");
        criteria.setCompany("");
        criteria.setDepartment("");
        criteria.setEmail("");
        criteria.setJobTitle("");
        criteria.setMobile("");
        criteria.setName("");
        criteria.setPageIndex(0);
        criteria.setSortAsc(true);
        criteria.setSortField("");
        SearchResult<Contact> searchResult = null;
        
        // test admin can see all results when he search contact
        searchResult = contactRepo.searchByCriteria(criteria, 1, "ADMINISTRATOR");
        assertNotNull(searchResult);
        assertEquals(9, searchResult.getItems().size());
        
        // test designer only can see  assigned contact when he search contact
        searchResult = contactRepo.searchByCriteria(criteria, 2, "DESIGNER");
        assertNotNull(searchResult);
        assertEquals(6, searchResult.getItems().size());
        for(Contact c : searchResult.getItems()){
            assertNotSame((int)c.getId(), 3);
            assertTrue(c.getId() != 3);
            assertTrue(c.getId() != 6);
            assertTrue(c.getId() != 9);
        }
        
        // test editer only can see  assigned contact when he search contact
        searchResult = contactRepo.searchByCriteria(criteria, 3, "EDITOR");
        assertNotNull(searchResult);
        assertEquals(6, searchResult.getItems().size());
        for(Contact c : searchResult.getItems()){
            assertNotSame((int)c.getId(), 3);
            assertTrue(c.getId() != 3);
            assertTrue(c.getId() != 6);
            assertTrue(c.getId() != 9);
        }
        
    }

}
