package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;
import vn.kms.launch.contactmgr.service.ContactService;
import vn.kms.launch.contactmgr.util.EntityNotFoundException;
import vn.kms.launch.contactmgr.util.SearchResult;
import vn.kms.launch.contactmgr.util.ValidationException;

@RestController
@RequestMapping(value = "/api/contacts")
public class ContactController {
    @Autowired
    private ContactService contactService;

    @RequestMapping(value = "/{id}", method = GET)
    public ResponseEntity<Contact> getContact(@PathVariable int id) {
        Contact contact = contactService.getContact(id);
        return new ResponseEntity<>(contact, (contact == null) ? NOT_FOUND : OK);
    }

    @RequestMapping(value = "/search", method = POST)
    public SearchResult<Contact> searchContact(@RequestBody ContactSearchCriteria criteria) {
        return contactService.searchContacts(criteria);
    }

    @RequestMapping(value = "/validate", method = POST)
    public ResponseEntity<Object> validateContact(@RequestBody Contact contact) {
        try {
            contactService.validateContact(contact);
            return new ResponseEntity<>(OK);
        } catch (ValidationException e) {
            return new ResponseEntity<Object>(e.getErrors(), BAD_REQUEST);
        }
    }

    @RequestMapping(method = POST)
    public ResponseEntity<?> createContact(@RequestBody Contact contact) {
        return saveContact(contact, null);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<?> updateContact(@PathVariable int id,
                                           @RequestBody Contact contact) {
        return saveContact(contact, id);
    }

    @RequestMapping(value = "/{id}", method = DELETE)
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        int deleteId = contactService.deleteContacts(id);

        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : NO_CONTENT);
    }

    @RequestMapping(method = DELETE)
    public ResponseEntity<?> deleteContacts(@RequestParam int... ids) {

        if (ids.length == 0) {
            return new ResponseEntity<>(BAD_REQUEST);
        }

        int deleteId = contactService.deleteContacts(ids);
        return new ResponseEntity<>((deleteId == 0) ? NOT_FOUND : OK);
    }

    private ResponseEntity<?> saveContact(Contact contact, Integer contactId) {
        try {
            Contact savedContact = contactService.saveContact(contact, contactId);
            return new ResponseEntity<>(savedContact, OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(NOT_FOUND);
        } catch (ValidationException e) {
            Map<String, Object> returnObj = new HashMap<>();
            returnObj.put("data", contact);
            returnObj.put("errors", e.getErrors());
            return new ResponseEntity<>(returnObj, BAD_REQUEST);
        }
    }

}
