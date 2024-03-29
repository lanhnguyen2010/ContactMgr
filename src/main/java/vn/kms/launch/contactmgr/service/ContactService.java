package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.google.common.primitives.Ints;

import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.*;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;
import vn.kms.launch.contactmgr.util.AuthorizationException;
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

    public Contact getContact(int id) throws AuthorizationException {
        if(contactRepo.exists(id) && !hasPermissionEditAndDeleteContact(id)){
            throw new AuthorizationException();
        }
        return contactRepo.findOne(id);
    }

    @Transactional
    public Contact saveContact(Contact contact, Integer contactId)
            throws ValidationException, AuthorizationException {
        
        if (contact == null) {
            return null;
        }

        if (contactId != null && !contactRepo.exists(contactId)) {
            throw new EntityNotFoundException();
        }
        
        if(contactId != null && contactRepo.exists(contactId) && !hasPermissionEditAndDeleteContact(contactId)){
            throw new AuthorizationException();
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
        User user = SecurityUtil.getCurrentUser();
        List<Integer> assignedCompanies = null;
        if(!user.getRole().equals(Role.ADMINISTRATOR.name())){
            assignedCompanies = user.getAssignedCompanies();
            System.out.println("number assigned companies: " + assignedCompanies.size());
        }
        return contactRepo.getCompanyNames(assignedCompanies);
    }

    @Transactional
    public int deleteContacts(int... ids) throws AuthorizationException {
        if(!hasPermissionEditAndDeleteContact(ids)){
           throw new AuthorizationException();
        } 
        
        return contactRepo.deleteByIds(ids);
    }
    
    private boolean hasPermissionEditAndDeleteContact(int... contactIds){
        String role = SecurityUtil.getCurrentUserRole();
        if(role.equals(Role.ADMINISTRATOR.name())){
            return true;
        } else {
            List<Integer> assignedCompanyIds = SecurityUtil.getCurrentUser().getAssignedCompanies();
            List<Integer> assignedContactIds = contactRepo.getContactIds(assignedCompanyIds);
            if(assignedContactIds .containsAll(Ints.asList(contactIds))){
               return true;
            }
            
            return false;
        }
    }

    public SearchResult<Contact> searchContacts(ContactSearchCriteria criteria) {
        User user = SecurityUtil.getCurrentUser();
        List<Integer> assignedCompanies = null;
        if(!user.getRole().equals(Role.ADMINISTRATOR)){
            assignedCompanies = user.getAssignedCompanies();
        }
        return contactRepo.searchByCriteria(criteria,  assignedCompanies);
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
        String role = SecurityUtil.getCurrentUserRole();
        if(!StringUtils.isEmpty(role) && role.equals(Role.ADMINISTRATOR.name())){
            return companyRepo.findAll();
        }

        Integer userId = SecurityUtil.getCurrentUserId();
        if(userId != null){
            User user = userRepo.findOne(userId);
            return companyRepo.findAll(user.getAssignedCompanies());
        }
        return null;
    }

    @Transactional
    public Company saveCompany(Company company, int id) throws AuthorizationException {
        if (company != null) {
            validateCompany(company);
            if (id == 0) {
                // create a new company
                return companyRepo.save(company);
            } else {
                // update a existing company
                Integer userId = SecurityUtil.getCurrentUserId();
                if(userId != null){
                    User user = userRepo.findOne(userId);
                    if(!user.getRole().equals(Role.ADMINISTRATOR.name()) && !user.getAssignedCompanies().contains(id)){
                        throw new AuthorizationException();
                    }
                    
                    company.setId(id);
                    return companyRepo.save(company);
                }
            }
        }
        return null;
    }
}
