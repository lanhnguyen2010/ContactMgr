package vn.kms.launch.contactmgr.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
@Transactional(readOnly = true)
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;
	@Autowired
	private ApplicationContext appContext;
	@Autowired
    private GaugeService gaugeService;
    
    @Autowired
    private CounterService counterService;

	

	@Transactional
	public long loadContacts(String filePath) throws FileNotFoundException,
			IOException {
		// TODO Auto-generated method stub
		try (BufferedReader reader = new BufferedReader(
				new FileReader(filePath))) {
			return reader.lines()
			// .parallel()
					.map(this::parseContact).map(this::saveContact).count();
		}

	}


	private Contact parseContact(String contactLine) {
		String[] items = contactLine.split("\\|");
		if (items.length < 2) {
			throw new IllegalArgumentException("Invalid contact-line format: "
					+ contactLine);
		}

		Contact contact = new Contact();
		 contact.setId(Integer.parseInt(items[0]));
		 contact.setFirstName(items[1]);
		/*
		 * if (items.length > 2) { contact.setFullName(items[2]); } if
		 * (items.length > 3) { contact.setJobTitle(items[3]); } if
		 * (items.length > 4) { contact.setEmail(items[4]); } if (items.length >
		 * 5) { contact.setMobile(items[5]); } if (items.length > 6) {
		 * contact.setSkypeId(items[6]); }
		 */

		return contact;
	}

	
	public Contact saveContact(@Valid Contact contact) {
		if (contact == null) {
			return null;
		}

		// if (contact.getId() == 0) {
		// contact.setId(idGeneration.incrementAndGet());
		// }

		return contactRepo.save(contact);
	}

	@Transactional
	public Contact getContact() {
		// TODO Auto-generated method stub
		Contact contact = contactRepo.findOne(1);
		//System.out.print("TEXT.........."+ contact.getId());
		return contact;
	}
	
	

}
