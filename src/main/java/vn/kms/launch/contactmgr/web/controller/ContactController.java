package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.service.ContactService;

@RestController
@RequestMapping(value = "/api/contacts")
public class ContactController {
	@Autowired
	private ContactService contactService;
	
	/**
	 * Get detail of an existing contact
	 * @param id is ID of the contact we need get.
	 * @return "404 code" if not found or "200 code and data of contact"
	 */
	@RequestMapping(value = "/{id}", method = GET)
	public ResponseEntity<Contact> getContact(@PathVariable int id) {
		Contact contact = contactService.getContact(id);
		if (contact == null) {
			return new ResponseEntity<Contact>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Contact>(contact, HttpStatus.OK);
	}

	/**
	 * Delete a contact
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@RequestParam int id) {

		int deleteId = contactService.deleteContacts(id);
		if (deleteId == 0)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * deletes contact
	 * 
	 * @param ids
	 */

	@RequestMapping(value = "/deletes", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deletes(@RequestParam Integer... ids) {

		int deleteId = contactService.deleteContacts(ids);
		if (deleteId == 0)
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}
}
