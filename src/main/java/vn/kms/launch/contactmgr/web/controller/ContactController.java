package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;

import javax.validation.Valid;

import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.search.ContactSearchCriteria;
import vn.kms.launch.contactmgr.service.ContactService;
import vn.kms.launch.contactmgr.service.validator.ContactValidator;

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

    @RequestMapping(value="/search", method = POST)
    public HashMap<String, Object> searchContact(@RequestParam ("page") int page,
                                              @RequestParam (value="pageSize", defaultValue="10") int pageSize,
                                              @RequestBody ContactSearchCriteria criteria) {
        return contactService.searchContacts(criteria, page, pageSize);
    }
    
    @RequestMapping(value="/validate", method = POST)
    public HashMap<String, String> validateContact(@RequestBody @Valid Contact contact) {
    	HashMap<String, String> returnValue = new HashMap<String, String>();
    	ContactValidator contactValidator = new ContactValidator();
    	
    	
//    	returnValue.put("displayName", "invalid displayName!");
    	returnValue.put("firstName", "invalid displayName!");
    	returnValue.put("lastName", "invalid displayName!");
    	returnValue.put("email", "invalid displayName!");
    	returnValue.put("mobile", "invalid displayName!");
    	returnValue.put("phone", "invalid displayName!");
    	returnValue.put("fax", "invalid displayName!");
    	returnValue.put("country", "invalid displayName!");
    	
        return returnValue;
    }
    
    /**
     *
     * @param id
     * @param contact
     * @return
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<Contact> updateContact(@PathVariable int id, @RequestBody @Valid Contact contact) {
    	
		Contact returnContact = contactService.saveContact(contact);
    	
		if (returnContact == null) { 
    		return new ResponseEntity<Contact>(returnContact, HttpStatus.BAD_REQUEST);
    	}

        return new ResponseEntity<Contact>(returnContact, HttpStatus.OK);
    }

	/**
	 * Return 404(Not Found)  code if not contact associated to ID is not found
	 * Return 204(No Content)  code if deleted successfully
	 */
	@RequestMapping(value = "/{id}", method = DELETE)
	public ResponseEntity<Void> deleteContact(@PathVariable int id) {
		
		int deleteId = contactService.deleteContacts(id);
		//receive  id with method deleteContact() from UI

		if (deleteId == 0) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Return 400(Bad Request)  code if ids param is null
	 * Return 404(Not Found)  code if not contact associated to ID is not found
	 * Return 200(OK) code and the actual number of Contact that deleted
	 */
	@RequestMapping(method = DELETE)
	public ResponseEntity<Integer> deleteContacts(@RequestParam int... ids) {

		if(ids.length == 0){
			return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
		}
		
		int deleteId = contactService.deleteContacts(ids);
		if (deleteId == 0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Integer>(deleteId,HttpStatus.OK);
	}

	/**
	 * Create a new contact
	 * @param contact
	 * @return
	 */
	@RequestMapping(method = POST)
	public ResponseEntity<Contact> create(@RequestBody @Valid Contact contact) {
		
		Contact returnContact = contactService.saveContact(contact);
    	
		if (returnContact == null) { 
    		return new ResponseEntity<Contact>(returnContact, HttpStatus.BAD_REQUEST);
    	}

        return new ResponseEntity<Contact>(returnContact, HttpStatus.OK);
	}
	
}
