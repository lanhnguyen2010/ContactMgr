package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
@Transactional(readOnly = true)
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;

	/**
	 * Get a contact by id
	 * @param id is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	@Transactional
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
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
