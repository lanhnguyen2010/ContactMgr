package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;

	/**
	 * Get a contact by id
	 * @param id is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}
}
