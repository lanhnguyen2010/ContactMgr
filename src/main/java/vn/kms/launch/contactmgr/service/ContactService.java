package vn.kms.launch.contactmgr.service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private CompanyRepository companyRepo;

	/**
	 * Get a contact by id
	 * 
	 * @param id
	 *            is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}

    public Company getCompany(Integer id) {
        return companyRepo.findOne(id);
    }

    @Transactional
    public Contact saveContact(@Valid Contact contact) {

        if (contact == null) {
            return null;
        }

        return contactRepo.save(contact);
    }
    
    @Transactional
    public Company saveCompany(@Valid Company company) {

        if (company == null) {
            return null;
        }

        return companyRepo.save(company);
    }
	
	public HashMap<String, Object> searchContacts(String data)
			throws JSONException {

		JSONObject jOb = new JSONObject(data);

		JSONObject jObContact = (JSONObject) jOb.get("contact");

		String name = jObContact.getString("name");

		String mobile = jObContact.getString("mobile");

		String email = jObContact.getString("email");

		String jobTitle = jObContact.getString("department");

		String department = jObContact.getString("department");

		String company = jObContact.getString("company");

		int page = jOb.getInt("page");

		int pageSize = jOb.getInt("pagesize");

		List<Contact> contacts = contactRepo.searchContacts(name, mobile,
				email, jobTitle, department, company);

		HashMap<String, Object> obContact = new HashMap<String, Object>();
		obContact.put("contact", contacts);
		obContact.put("total", contacts.size());
		return obContact;

	};

	/**
	 * Service form function delete
	 * 
	 * @param ids
	 * @return total contact are deleted
	 */
	@Transactional
	public int deleteContacts(Integer... ids) {
		return contactRepo.deleteContacts(ids);
	}

	@Transactional
	public int createContact(Contact contact) {
		// contact.setId(null);
		Integer companyId = contact.getWork().getCompanyId();
		if (companyId != null && companyId != 0) {
			// create a new contact with existing company
			return createContactWithExistCompany(contact);
		} else {
			if (contact.getWork().getCompany() == null) {
				// create a new contact without company
				return createContactWithoutCompany(contact);
			} else {
				// create a new contact with new company
				return createContactWithNewCompany(contact);
			}
		}
	}

	private int createContactWithNewCompany(Contact contact) {
		Company newCompany = companyRepo.save(contact.getWork().getCompany());
		if (newCompany != null) {
			contact.getWork().setCompany(newCompany);
			contact.getWork().setCompanyId(newCompany.getId());
			Contact newContact = contactRepo.save(contact);
			if (newContact != null) {
				return newContact.getId();
			}
		}
		return 0;
	}

	private int createContactWithExistCompany(Contact contact) {
		Company company = companyRepo.findOne(contact.getWork().getCompanyId());
		if (company != null) {
			Contact newContact = contactRepo.save(contact);
			return newContact.getId();
		}
		return 0;
	}

	private int createContactWithoutCompany(Contact contact) {
		Contact newContact = contactRepo.save(contact);
		if (newContact != null) {
			return newContact.getId();
		}
		return 0;

	}
}
