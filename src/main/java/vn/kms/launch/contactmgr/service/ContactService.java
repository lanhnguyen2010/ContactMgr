package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.*;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.SecurityUtil;
import vn.kms.launch.contactmgr.util.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

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
    private PhotoRepository photoRepo;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private Validator validator;

    public Contact getContact(int id) {
        return contactRepo.findOne(id);
    }

    @Transactional
    public Contact saveContact(Contact contact, Integer contactId)
            throws ValidationException {
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

    @Transactional
    public List<Itemized> getCompanyNames() {
        return contactRepo.getCompanyNames(SecurityUtil.getCurrentUserId());
    }

    @Transactional
    public int deleteContacts(int... ids) {
        return contactRepo.deleteByIds(ids);
    }

    public SearchResult<Contact> searchContacts(ContactSearchCriteria criteria) {
        return contactRepo.searchByCriteria(criteria, 
                SecurityUtil.getCurrentUserId(), 
                SecurityUtil.getCurrentUserRole());
    }

    public void validateContact(Contact contact) throws ValidationException {
        Set<ConstraintViolation<Contact>> violations = validator
                .validate(contact);
        if (!violations.isEmpty()) {
            throw new ValidationException(
                    violations.toArray(new ConstraintViolation[0]));
        }
    }

    public void validateCompany(Company company) throws ValidationException {
        Set<ConstraintViolation<Company>> violations = validator
                .validate(company);
        if (!violations.isEmpty()) {
            throw new ValidationException(
                    violations.toArray(new ConstraintViolation[0]));
        }
    }

    public List<Country> getCountries() {
        return countryRepo.findAll();
    }

    public List<Company> getAllCompanies() {
        if(SecurityUtil.getCurrentUserRole().equals(Role.ADMINISTRATOR.name())){
            return companyRepo.findAll();
        }

        User user = userRepo.findOne(SecurityUtil.getCurrentUserId());
        return companyRepo.findAll(user.getAssignedCompanies());
    }

    @Transactional
    public Company saveCompany(Company company, int id) {
        if (company != null) {
            validateCompany(company);
            if (id == 0) {
                // create a new company
                return companyRepo.save(company);
            } else {
                // update a existing company
                company.setId(id);
                return companyRepo.save(company);
            }
        }
        return null;
    }
}
