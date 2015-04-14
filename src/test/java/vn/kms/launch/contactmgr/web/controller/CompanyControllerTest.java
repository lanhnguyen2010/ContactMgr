package vn.kms.launch.contactmgr.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import vn.kms.launch.contactmgr.ContactMgrApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContactMgrApp.class)
@WebAppConfiguration
public class CompanyControllerTest {

    @Autowired
    CompanyController companyController;

    @Test
    public void getCompaniesTest() throws Exception{
        ResponseEntity<ArrayList<HashMap<String, Object>>> companyNames =  companyController.getCompany();
        System.out.println(companyNames);
    }
}
