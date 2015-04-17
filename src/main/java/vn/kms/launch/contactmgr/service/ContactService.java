package vn.kms.launch.contactmgr.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.CompanyRepository;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactRepository;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;
import vn.kms.launch.contactmgr.domain.contact.Country;
import vn.kms.launch.contactmgr.domain.contact.CountryRepository;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

@Service
@Transactional(readOnly = true)
public class ContactService {
    @Autowired
    private ContactRepository contactRepo;

    @Autowired
    private CompanyRepository companyRepo;
    
    @Autowired
    private CountryRepository countryRepo;

    @Autowired
    private Validator validator;

    public Contact getContact(int id) {
        return contactRepo.findOne(id);
    }

    @Transactional
    public Contact saveContact(Contact contact, Integer contactId) throws ValidationException {
        if (contact == null) {
            return null;
        }

        if (contactId != null && !contactRepo.exists(contactId)) {
            throw new EntityNotFoundException();
        }

        contact.setId(contactId);
        validateContact(contact);

        Work work = contact.getWork();
        if (work != null) {
            Integer companyId = contact.getWork().getCompanyId();
            if (companyId != null && !companyRepo.exists(companyId)) {
                return null;
            }

            Company company = contact.getWork().getCompany();
            if (company != null) {
                company.setId(companyId);
                companyRepo.save(company);
            }
        }

        return contactRepo.save(contact);
    }

    public List<Itemized> getCompanyNames() {
        return contactRepo.getCompanyNames();
    }

    @Transactional
    public int deleteContacts(int... ids) {
        return contactRepo.deleteByIds(ids);
    }

    public SearchResult searchContacts(ContactSearchCriteria criteria) {
        return contactRepo.searchByCriteria(criteria);
    }

    public void validateContact(Contact contact) throws ValidationException {
        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        if (!violations.isEmpty()) {
            throw new ValidationException(violations.toArray(new ConstraintViolation[0]));
        }
    }
    
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
