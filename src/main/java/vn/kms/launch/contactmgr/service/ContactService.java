package vn.kms.launch.contactmgr.service;

import java.util.List;

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
	@Transactional
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}

	@Transactional
	public int deleteContacts(Integer... ids) {
		return contactRepo.deleteContacts(ids);
	}

}
