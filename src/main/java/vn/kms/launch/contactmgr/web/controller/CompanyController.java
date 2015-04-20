package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.service.ContactService;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.ValidationException;

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/names", method = GET)
    public ResponseEntity<List<Itemized>> getCompanyNames() {
        return new ResponseEntity<>(contactService.getCompanyNames(), HttpStatus.OK);
    }
    
    @RequestMapping(method = GET)
    public ResponseEntity<List<Company>> getAllCompanies(){
        List<Company> companies = contactService.getAllCompanies();
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<Company> getCompanie(@PathVariable int id){
        Company company = contactService.getCompany(id);
        
        return new ResponseEntity<>(company, (company == null)? NOT_FOUND : OK);
    }
    
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<?> saveCompany(@PathVariable int id, @RequestBody Company company) {
        return save(id, company);
    }
    
    @RequestMapping(method = POST)
    public ResponseEntity<?> createCompany(@RequestBody Company company) {
        return save(0, company);
    }
    
    private ResponseEntity<?> save(int id, Company company){
        try {
            Company savedCompany = contactService.saveCompany(company, id);
            return new ResponseEntity<>(savedCompany, OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (ValidationException e) {
            Map<String, Object> returnObj = new HashMap<>();
            returnObj.put("data", company);
            returnObj.put("errors", e.getErrors());
            return new ResponseEntity<>(returnObj, BAD_REQUEST);
        }
    }

}
