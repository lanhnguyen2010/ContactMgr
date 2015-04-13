package vn.kms.launch.contactmgr.web.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.kms.launch.contactmgr.ContactMgrApp;
import vn.kms.launch.contactmgr.domain.contact.Address;
import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.domain.contact.Home;
import vn.kms.launch.contactmgr.service.ContactService;

import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class UpdateCreateContactTest {

    @Autowired
    private ContactService contactService;

    @Autowired
    private ContactController contactController;

    private JSONObject createTest;
    private JSONObject updateTest1;
    private JSONObject updateTest2;
    private JSONObject updateTest3;
    private JSONObject updateTest4;


    public Home parseHome(JSONObject jsonHome) {
        if (jsonHome == null) return null;

        Home home = new Home();

        String phone = (String) jsonHome.get("phone");
        String fax = (String) jsonHome.get("fax");
        Address address = parseAddress((JSONObject) jsonHome.get("address"));

        home.setAddress(address);
        home.setPhone(phone);
        home.setFax(fax);

        return home;
    }


    public Work parseWork(JSONObject jsonWork) {
        if (jsonWork == null) return null;

        Work work = new Work();

        String title = (String) jsonWork.get("title");
        String department = (String) jsonWork.get("department");
        String companyIdString = (String) jsonWork.get("companyId");
        Integer companyId = companyIdString == null ? null : Integer.parseInt(companyIdString);
        Company company = parseCompany(companyId, (JSONObject) jsonWork.get("company"));
        if (company == null) {
            if (companyId != null) {
                company = contactService.getCompany(companyId);
                if (company == null) System.out.println("Error");
            }
        } else {
            companyId = company.getId();
        }

        work.setTitle(title);
        work.setDepartment(department);
        work.setCompanyId(companyId);
        work.setCompany(company);

        return work;
    }


    public Address parseAddress(JSONObject jsonAddress) {
        if (jsonAddress == null) return null;

        Address address = new Address();

        Integer postalCode = (Integer) jsonAddress.get("postalCode");
        String addrLine1 = (String) jsonAddress.get("addrLine1");
        String addrLine2 = (String) jsonAddress.get("addrLine2");
        String city = (String) jsonAddress.get("city");
        String country = (String) jsonAddress.get("country");
        String state = (String) jsonAddress.get("state");

        address.setAddrLine1(addrLine1);
        address.setAddrLine2(addrLine2);
        address.setCity(city);
        address.setCountry(country);
        address.setState(state);
        address.setPostalCode(postalCode);

        return address;
    }

    public Company parseCompany(Integer id, JSONObject jsonCompany) {
        if (jsonCompany == null) return null;

        Company company = new Company();

        Address address = parseAddress((JSONObject) jsonCompany.get("address"));
        String code = (String) jsonCompany.get("code");
        String fax = (String) jsonCompany.get("fax");
        String logo = (String) jsonCompany.get("logo");
        String name = (String) jsonCompany.get("name");
        String phone = (String) jsonCompany.get("phone");
        String website = (String) jsonCompany.get("website");
        String message = (String) jsonCompany.get("message");

        company.setId(id);
        company.setAddress(address);
        company.setCode(code);
        company.setFax(fax);
        company.setLogo(logo);
        company.setMessage(message);
        company.setName(name);
        company.setPhone(phone);
        company.setWebsite(website);

        contactService.saveCompany(company);
        return company;
    }

    public Contact parseContact(JSONObject jsonContact) {
        if (jsonContact == null) return null;

        Contact contact = new Contact();

        Date birthday = null;
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-mm-dd");
            birthday = dateFormatter.parse((String) jsonContact.get("birthday"));
        } catch (Exception exception) {
            System.out.println(exception.toString());
        }
        String email = (String) jsonContact.get("email");
        String firstName = (String) jsonContact.get("firstName");
        String gender = (String) jsonContact.get("gender");
        String lastName = (String) jsonContact.get("lastName");
        String middleName = (String) jsonContact.get("middleName");
        String mobile = (String) jsonContact.get("mobile");
        String photo = (String) jsonContact.get("photo");
        Work work = parseWork((JSONObject) jsonContact.get("work"));
        Home home = parseHome((JSONObject) jsonContact.get("home"));

        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setMiddleName(middleName);
        contact.setBirthday(birthday);
        contact.setEmail(email);
        contact.setGender(gender);
        contact.setMobile(mobile);
        contact.setPhoto(photo);
        contact.setWork(work);
        contact.setHome(home);

        return contact;
    }

    @Before
    public void setUp() throws Exception {
        JSONParser parser = new JSONParser();
        String filePath = getClass().getResource("/etc/updateContact-test.json").getFile();
        JSONObject inputTest = (JSONObject) parser.parse(new FileReader(filePath));

        createTest = (JSONObject) inputTest.get("createTest");
        updateTest1 = (JSONObject) inputTest.get("updateTest1");
        updateTest2 = (JSONObject) inputTest.get("updateTest2");
        updateTest3 = (JSONObject) inputTest.get("updateTest3");
        updateTest4 = (JSONObject) inputTest.get("updateTest4");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdateContact() throws Exception {
        Contact contact;
        Contact contactTest;
        Integer idContact;
        ResponseEntity<HashMap<String, Object>> contactResponseEntity;
        HttpStatus httpStatus;

        // Test create contact
        contactTest = parseContact(createTest);
        contactResponseEntity = contactController.createContact(contactTest);
        httpStatus = contactResponseEntity.getStatusCode();
        assertThat(httpStatus, is(equalTo(HttpStatus.OK)));
        contact = (Contact) contactResponseEntity.getBody().get("data");
        assertThat(contact.getFirstName(), is(equalTo(contactTest.getFirstName())));
        assertThat(contact.getMiddleName(), is(equalTo(contactTest.getMiddleName())));
        assertThat(contact.getLastName(), is(equalTo(contactTest.getLastName())));
        assertThat(contact.getEmail(), is(equalTo(contactTest.getEmail())));
        assertThat(contact.getPhoto(), is(equalTo(contactTest.getPhoto())));
        assertThat(contact.getMobile(), is(equalTo(contactTest.getMobile())));
        assertThat(contact.getGender(), is(equalTo(contactTest.getGender())));
        assertThat(contact.getHome().getFax(), is(equalTo(contactTest.getHome().getFax())));
        assertThat(contact.getHome().getPhone(), is(equalTo(contactTest.getHome().getPhone())));
        assertThat(contact.getHome().getAddress().getAddrLine1(),
            is(equalTo(contactTest.getHome().getAddress().getAddrLine1())));
        assertThat(contact.getHome().getAddress().getAddrLine2(),
            is(equalTo(contactTest.getHome().getAddress().getAddrLine2())));
        assertThat(contact.getHome().getAddress().getCity(),
            is(equalTo(contactTest.getHome().getAddress().getCity())));
        assertThat(contact.getHome().getAddress().getCountry(),
            is(equalTo(contactTest.getHome().getAddress().getCountry())));
        assertThat(contact.getHome().getAddress().getPostalCode(),
            is(equalTo(contactTest.getHome().getAddress().getPostalCode())));
        assertThat(contact.getHome().getAddress().getState(),
            is(equalTo(contactTest.getHome().getAddress().getState())));
        assertThat(contact.getWork().getTitle(),
            is(equalTo(contactTest.getWork().getTitle())));
        assertThat(contact.getWork().getDepartment(),
            is(equalTo(contactTest.getWork().getDepartment())));
        assertThat(contact.getWork().getCompanyId(),
            is(equalTo(contactTest.getWork().getCompanyId())));
        assertThat(contactTest.getWork().getCompanyId(),
            is(equalTo(null)));
        assertThat(contact.getWork().getCompany(),
            is(equalTo(contactTest.getWork().getCompany())));
        assertThat(contactTest.getWork().getCompany(),
            is(equalTo(null)));
        idContact = contact.getId();
        assertThat(idContact, is(equalTo(1)));

        // Case: update contact info only (email)
        contactTest = parseContact(updateTest1);
        contactResponseEntity = contactController.updateContact(idContact, contactTest);
        httpStatus = contactResponseEntity.getStatusCode();
        contact = (Contact) contactResponseEntity.getBody().get("data");
        assertThat(contact.getEmail(), is(equalTo(contactTest.getEmail())));
        assertThat(httpStatus, is(equalTo(HttpStatus.OK)));

        // Case: update contact info but new company
        contactTest = parseContact(updateTest2);
        contactResponseEntity = contactController.updateContact(idContact, contactTest);
        httpStatus = contactResponseEntity.getStatusCode();
        contact = (Contact) contactResponseEntity.getBody().get("data");
        assertThat(contact.getWork().getCompanyId(), is(equalTo(contactTest.getWork().getCompanyId())));
        assertThat(contact.getWork().getCompany().getMessage(),
            is(equalTo(contactTest.getWork().getCompany().getMessage())));
        assertThat(httpStatus, is(equalTo(HttpStatus.OK)));

        // Case: update company info
        contactTest = parseContact(updateTest3);
        contactResponseEntity = contactController.updateContact(idContact, contactTest);
        httpStatus = contactResponseEntity.getStatusCode();
        contact = (Contact) contactResponseEntity.getBody().get("data");
        assertThat(contact.getWork().getCompanyId(), is(equalTo(contactTest.getWork().getCompanyId())));
        assertThat(contact.getWork().getCompany().getMessage(),
            is(equalTo(contactTest.getWork().getCompany().getMessage())));
        assertThat(httpStatus, is(equalTo(HttpStatus.OK)));

        // Case: update company info but company is not existed
        contactTest = parseContact(updateTest4);
        contactResponseEntity = contactController.updateContact(idContact, contactTest);
        httpStatus = contactResponseEntity.getStatusCode();
        assertThat(httpStatus, is(equalTo(HttpStatus.BAD_REQUEST)));
    }
}
