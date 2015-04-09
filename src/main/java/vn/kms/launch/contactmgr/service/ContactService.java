package vn.kms.launch.contactmgr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;
}
