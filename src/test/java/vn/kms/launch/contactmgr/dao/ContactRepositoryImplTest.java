package vn.kms.launch.contactmgr.dao;

import java.util.ArrayList;
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
        // test capability can get name of all companies when companyIds == null
        List<Itemized> companyNames = contactRepo.getCompanyNames(null);
        assertNotNull(companyNames);
        assertEquals(3, companyNames.size());
        
        // test capability  designer can get name of companies by companyIds
        List<Integer> companyIds = new ArrayList<>();
        companyIds.add(1);
        companyIds.add(2);
        companyNames = contactRepo.getCompanyNames(companyIds);
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
        
        // test capability can see search results of all contact when companyIds equals null
        searchResult = contactRepo.searchByCriteria(criteria, null);
        assertNotNull(searchResult);
        assertEquals(9, searchResult.getItems().size());
        
        // test câpbility only can see  search results of contacts has company id on companyIds
        List<Integer> companyIds = new ArrayList<>();
        companyIds.add(1);
        companyIds.add(2);
        searchResult = contactRepo.searchByCriteria(criteria, companyIds);
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
