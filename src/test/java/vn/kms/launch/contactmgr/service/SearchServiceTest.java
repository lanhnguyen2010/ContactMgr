package vn.kms.launch.contactmgr.service;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class SearchServiceTest {
    @Autowired
    private ContactService contactService;


    @SuppressWarnings("deprecation")
    public Contact readContactFile() throws JsonParseException, IOException {
        Contact contact = new Contact();
        JsonFactory jFactory = new JsonFactory();
        System.out.println("read");

//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/etc/contact.json");
//		JsonParser jParser = jFactory.createJsonParser(inputStream);
//		jParser.nextToken();
//		System.out.println(jParser.getText());
        ObjectMapper mapper = new ObjectMapper();
        ContactSearchCriteria criteria = mapper.readValue(getClass().getClassLoader().getResourceAsStream("/etc/searchcriteria/searchcriteria1.json"), ContactSearchCriteria.class);
        System.out.println("result" + criteria.getName());


        return contact;

    }

    @Test
    public void testContactService() throws JsonParseException, IOException {
        Contact contact = readContactFile();

    }


}
