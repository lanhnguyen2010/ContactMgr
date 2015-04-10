package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;

    @Autowired
    private CompanyRepository companyRepo;

	/**
	 * Get a contact by id
	 * @param id is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	@Transactional
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}

    @Transactional
    public Contact saveContact(@Valid Contact contact) {

        if (contact == null) {
            return null;
        }

        return contactRepo.save(contact);
    }

    @Transactional
    public Company getCompany(Integer id) {
        return companyRepo.findOne(id);
    }

    @Transactional
    public List<Company> getAllCompany() {
        return companyRepo.findAll();
    }

    @Transactional
    public Company saveCompany(@Valid Company company) {

        if (company == null) {
            return null;
        }

        return companyRepo.save(company);
    }

	/**
	 * Service form function delete
	 * @param ids
	 * @return
	 */
	@Transactional
	public int deleteContacts(Integer... ids) {
		return contactRepo.deleteContacts(ids);
	}

}
