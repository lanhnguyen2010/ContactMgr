package vn.kms.launch.contactmgr.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.CompanyRepository;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactRepository;
import vn.kms.launch.contactmgr.domain.contact.Country;
import vn.kms.launch.contactmgr.domain.contact.CountryRepository;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
@Service
@Transactional(readOnly = true)
public class ContactService {
    
    private CountryRepository countryRepo;

    @Autowired
    private PhotoRepository photoRepo;
    
    @Autowired
    
    public List<Country> getCountries(){
        return countryRepo.findAll();
    }

    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    @Transactional
    public Company saveCompany(Company company, int id) {
        if(company != null){
            if(id == 0){
                // create a new company
                return companyRepo.save(company);
            } else{
                // update a existing company
                
                company.setId(id);
                return companyRepo.save(company);
            }
        }
        return null;
    }
}