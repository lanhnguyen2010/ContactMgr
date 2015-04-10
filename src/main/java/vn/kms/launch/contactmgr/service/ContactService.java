package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

import scala.annotation.meta.setter;
import scala.util.parsing.json.JSON;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
public class ContactService {
	@Autowired
	private ContactRepository contactRepo;
	
	private EntityManager em;
	private Session session;

	/**
	 * Get a contact by id
	 * @param id is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}
	
	public static class ContactSearchCriteria {
        private String name;
        private String mobile;
        private String email;
        private String jobTitle;
        private String department;
        private String company;
        
        private void setName(String name){
            this.name = name;
        }
        
        private void setMobile(String mobile){
            this.mobile = mobile;
        }
        
        private void setEmail(String email){
            this.email = email;
        }
        
        private void setJobTitle(String jobTitle){
            this.jobTitle = jobTitle;
        }
        
        private void setDepartment(String department){
            this.department = department;
        }
        
        private void setCompany(String company) {
            this.company = company;
        }
    }

    public List<Contact> searchContacts(ContactSearchCriteria criteria) {
    	
        StringBuilder queryString = null;
        queryString.append("select c from Contact c where ");        		
        if (!StringUtils.isEmpty(criteria.name)){ 
            if (criteria.name.contains("\\*")){
                criteria.name.replace("\\*", "\\%");
                queryString.append("lower(c.firstName) like :name "
                           + "or lower(c.middleName) like :name "
                           + "or lower(c.lastName) like :name ");
    	    }else{
                queryString.append("lower(c.firstName) =:name ");
                queryString.append("or lower(c.middleName) =:name ");
                queryString.append("or lower(c.lastName) =:name ");
    	        
    	    }
        }
        
        if(queryString.length() != ("select c from Contact c where ").length()){
          queryString.append(" and ");
        }
        if (!StringUtils.isEmpty(criteria.mobile)){
            if (criteria.mobile.contains("\\*")){
                criteria.mobile.replace("\\*", "\\%");
                queryString.append("lower(c.mobile) like :mobile ");
            }else {
                queryString.append("lower(c.mobile) =:mobile ");
            }
        }
        
        if(queryString.length() != ("select c from Contact c where ").length()){
            queryString.append(" and ");
          }
        if (!StringUtils.isEmpty(criteria.email)){
            if (criteria.email.contains("\\*")){
                criteria.email.replace("\\*", "\\%");
                queryString.append("lower(c.email) like :email ");
            }else {
                queryString.append("lower(c.email) =:email ");
            }
        }
        
        if(queryString.length() != ("select c from Contact c where ").length()){
            queryString.append(" and ");
          }
        if (!StringUtils.isEmpty(criteria.jobTitle)){
            if (criteria.jobTitle.contains("\\*")){
                criteria.jobTitle.replace("\\*", "\\%");
                queryString.append("lower(c.work.title) like :jobTitle ");
            }else {
                queryString.append("lower(c.work.title) =:jobTitle ");
            }
        }
        
        if(queryString.length() != ("select c from Contact c where ").length()){
            queryString.append(" and ");
          }
        if (!StringUtils.isEmpty(criteria.department)){
            if (criteria.department.contains("\\*")){
                criteria.department.replace("\\*", "\\%");
                queryString.append("lower(c.work.department) like :department ");
            }else {
                queryString.append("lower(c.work.department) =:department ");
            }
        }
        
        if(queryString.length() != ("select c from Contact c where ").length()){
            queryString.append(" and ");
          }
        if (!StringUtils.isEmpty(criteria.company)){
            if (criteria.company.contains("\\*")){
                criteria.company.replace("\\*", "\\%");
                queryString.append("lower(c.work.company.name) like :company ");
            }else {
                queryString.append("lower(c.work.company.name) =:company ");
            }
        }
        TypedQuery<Contact> query = em.createQuery(queryString.toString(),Contact.class).setParameter("name", criteria.name)
        		                                                    .setParameter("email", criteria.email)
        		                                                    .setParameter("mobile", criteria.mobile)
        		                                                    .setParameter("jobTitle",criteria.jobTitle)
        		                                                    .setParameter("company", criteria.company)
        		                                                    .setParameter("department", criteria.department);
        List<Contact> contacts = query.getResultList();

        



		
		
		
		return contacts;
		
	}
	
	public int countContacts(ContactSearchCriteria criteria, int pageIndex, int pageSize) {
		if (!StringUtils.isEmpty(criteria.name)) {
			
		}
		int total = 0;
		return total;
	}

        
	
}
