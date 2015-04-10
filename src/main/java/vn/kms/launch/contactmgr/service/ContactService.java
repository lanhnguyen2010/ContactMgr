package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;

	/**
	 * Get a contact by id
	 * @param id is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}
	
    public HashMap<String, Object> searchContacts(String data) throws JSONException{
    	
        JSONObject jOb = new JSONObject(data);
        
        JSONObject jObContact = (JSONObject) jOb.get("contact");
        
        String name = jObContact.getString("name");
        System.out.println("name :" + name);
        
        String mobile = jObContact.getString("mobile");
        
        String email = jObContact.getString("email");
        
        String jobTitle = jObContact.getString("department");
        
        String department = jObContact.getString("department");
        
        String company = jObContact.getString("company");
        
        int page = jOb.getInt("page");
        
        int pageSize = jOb.getInt("pagesize");
        
        List<Contact> contacts = contactRepo.searchContacts(name, mobile, email, jobTitle, department, company);
        
        List<Contact> resultContacts = new ArrayList<Contact>(contacts.subList(page*pageSize, (page + 1) * pageSize));
        JSONObject result = new JSONObject();
        
        HashMap<String, Object> obContact = new HashMap<String, Object>();
        obContact.put("contact",resultContacts);
        obContact.put("total",contacts.size());
        return obContact;
        
	};
}
