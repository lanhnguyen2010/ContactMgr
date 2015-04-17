package vn.kms.launch.contactmgr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.service.ContactService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Created by thoong on 4/10/2015.
 */

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {

    @Autowired
    private ContactService contactService;

    /**
     * Return: [{"id": valueOfId, "name": valueOfName}, {"id": valueOfId, "name": valueOfName}]
     */
    @RequestMapping(value = "/names", method = GET)
    public ResponseEntity<ArrayList<HashMap<String, Object>>> getCompany() {

        ArrayList<HashMap<String, Object>> companyNames = new ArrayList<HashMap<String, Object>>();
        List<Company> companyList = contactService.getAllCompany();
        int length = companyList.size();
        int pos = 0;

        while (pos < length) {
            Company company = companyList.get(pos);
            HashMap<String, Object> aCompanyName = new HashMap<String, Object>();
            aCompanyName.put("id", company.getId());
            aCompanyName.put("name", company.getName());
            companyNames.add(aCompanyName);
            pos++;
        }

        return new ResponseEntity<>(companyNames, HttpStatus.OK);
    }

}
