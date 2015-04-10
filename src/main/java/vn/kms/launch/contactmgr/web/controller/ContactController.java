package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.service.ContactService;

import javax.validation.Valid;

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
     *
     * @param id
     * @param contact
     * @return
     */
    @RequestMapping(value = "/{id}", method = PUT)
    public ResponseEntity<Contact> updateContact(@PathVariable Integer id, @RequestBody @Valid Contact contact) {
        Work work = contact.getWork();

        if (work != null) {
            Integer companyId = contact.getWork().getCompanyId();
            System.out.println(companyId);
            Company company = contact.getWork().getCompany();

            if (companyId != null){
                Company com = contactService.getCompany(companyId);
                if (com == null) {
                    return new ResponseEntity<Contact>(contact, HttpStatus.BAD_REQUEST);
                }
            }
            if (company != null) {
                contactService.saveCompany(company);
            }
        }

        contactService.saveContact(contact);

        return new ResponseEntity<Contact>(contact, HttpStatus.OK);
    }

	/**
	 * Delete a contact.
	 * @param id
	 * @return
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
	 */
	@RequestMapping(value = "/delete/{id}", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> delete(@PathVariable int id) {

		int deleteId = contactService.deleteContacts(id);
		//receive  id with method deleteContact() from UI

		if (deleteId == 0) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	/**
	 * Delete a contact.
	 * @param id
	 * @return
	 * Return 404 not found code if not contact associated to ID is not found
	 * Return 200 success code if deleted successfully
	 */
	@RequestMapping(value = "/delete", method = DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Integer> deletes(@RequestParam Integer... ids) {

		int deleteId = contactService.deleteContacts(ids);
		if (deleteId == 0) {
			return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Integer>(deleteId,HttpStatus.OK);
	}
}
