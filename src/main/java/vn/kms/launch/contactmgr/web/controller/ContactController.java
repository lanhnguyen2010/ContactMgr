package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;

import javax.validation.Valid;

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

    /**
     * To update contact info(s),
     * @param id
     * @param contact
     * @return
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<HashMap<String, Object>> updateContact(@PathVariable int id,
                                                                 @RequestBody @Valid Contact contact) {
        Contact returnContact;
        HashMap<String, Object> bodyReturn = new HashMap<String, Object>();

       
		returnContact = contactService.saveContact(contact);
		if (returnContact == null) {
            bodyReturn.put("data", contact);
            HashMap<String, String> errors = new HashMap<String, String>();
            errors.put("companyId", "No companyId found");
            bodyReturn.put("errors",errors);
    		return new ResponseEntity<HashMap<String, Object>>(bodyReturn, HttpStatus.BAD_REQUEST);
    	} else {
            bodyReturn.put("data", returnContact);
            return new ResponseEntity<HashMap<String, Object>>(bodyReturn, HttpStatus.OK);
        }
    }

	/**
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
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
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
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
	public ResponseEntity<HashMap<String, Object>> createContact(@RequestBody @Valid Contact contact) {
        Contact returnContact;
        HashMap<String, Object> bodyReturn = new HashMap<String, Object>();

        returnContact = contactService.saveContact(contact);
        if (returnContact == null) {
            bodyReturn.put("data", contact);
            HashMap<String, String> errors = new HashMap<String, String>();
            errors.put("companyId", "No companyId found");
            bodyReturn.put("errors",errors);
            return new ResponseEntity<HashMap<String, Object>>(bodyReturn, HttpStatus.BAD_REQUEST);
        } else {
            bodyReturn.put("data", returnContact);
            return new ResponseEntity<HashMap<String, Object>>(bodyReturn, HttpStatus.OK);
        }
	}

}
