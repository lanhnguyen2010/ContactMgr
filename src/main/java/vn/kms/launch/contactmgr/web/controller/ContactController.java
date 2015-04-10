package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	 * 
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
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
	 */
	@RequestMapping(value = "/{contactId}", method = DELETE)
	public ResponseEntity<Void> deleteContact(@PathVariable int contactId) {
		
		int deleteId = contactService.deleteContacts(contactId);
		//receive  id with method deleteContact() from UI
		
		if (deleteId == 0) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
	 */
	@RequestMapping(method = DELETE)
	public ResponseEntity<Integer> deleteContacts(@RequestParam int... contactIds) {

		if(contactIds.length == 0){
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		
		int deleteId = contactService.deleteContacts(contactIds);
		if (deleteId == 0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Integer>(deleteId,HttpStatus.OK);
	}
}
