package vn.kms.launch.contactmgr.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.service.ContactService;

@RestController
@RequestMapping(value = "/api/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;

	public ContactService getContactService() {
		return contactService;
	}

	public void setContactService(ContactService contactService) {
		this.contactService = contactService;
	}
	@RequestMapping("/getcontact")
	public Contact getContact(){
		return contactService.getContact();
	}
}
