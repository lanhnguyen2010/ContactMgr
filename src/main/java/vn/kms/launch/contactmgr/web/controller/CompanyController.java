package vn.kms.launch.contactmgr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.service.ContactService;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping(value = "/api/companies")
public class CompanyController {

    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/names", method = GET)
    public ResponseEntity<List<Itemized>> getCompanyNames() {
        return new ResponseEntity<>(contactService.getCompanyNames(), HttpStatus.OK);
    }

}
