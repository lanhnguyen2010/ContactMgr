package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;

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

        Work work = contact.getWork();

        if (work != null) {
            Integer companyId = contact.getWork().getCompanyId();
            Company company = contact.getWork().getCompany();

            if (companyId != null) {
                Company com = getCompany(companyId);
                if (com == null) {
                	return null;
                }
            }
            if (company != null) {
                saveCompany(company);
            }
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

	public HashMap<String, Object> searchContacts(String data) throws JSONException {
        JSONObject jOb        = new JSONObject(data);
        JSONObject jObContact = (JSONObject) jOb.get("contact");
        String name           = jObContact.getString("name");
        String mobile         = jObContact.getString("mobile");
        String email          = jObContact.getString("email");
        String jobTitle       = jObContact.getString("jobTitle");
        String department     = jObContact.getString("department");
        String company        = jObContact.getString("company");
        int page              = jOb.getInt("page");
        int pageSize          = jOb.getInt("pageSize");

        List<Contact> contacts = contactRepo.searchContacts(name, mobile, email, jobTitle, department, company);
        int contactsSize = contacts.size();
        int totalPages = contactsSize / pageSize;

        if (contactsSize % pageSize != 0) {
        	totalPages++;
        }

        if (page > totalPages) {
        	page = totalPages;
        }

        if (page < 1) {
        	page = 1;
        }

        int fromIndex = (page - 1) * pageSize;
        int toIndex = (page * pageSize > contactsSize) ? contactsSize : page * pageSize;
        List<Contact> resultContacts = new ArrayList<Contact>(contacts.subList(fromIndex, toIndex));

        HashMap<String, Object> obContact = new HashMap<String, Object>();
        obContact.put("contact", resultContacts);
        obContact.put("total", contactsSize);

        return obContact;
    };
}
