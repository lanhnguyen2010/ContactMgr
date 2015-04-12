package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.domain.search.ContactSearchCriteria;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;

@Service
@Transactional(readOnly = true)
public class ContactService {

	private static final String C_LAST_NAME = "c.lastName";

	private static final String C_MIDDLE_NAME = "c.middleName";

	private static final String C_WORK_COMPANY_NAME = "c.work.company.name";

	private static final String C_WORK_DEPARTMENT = "c.work.department";

	private static final String C_WORK_TITLE = "c.work.title";

	private static final String C_EMAIL = "c.email";

	private static final String C_MOBILE = "c.mobile";

	private static final String C_FIRST_NAME = "c.firstName";

	private static final String SELECT_C_FROM_CONTACT_C = "select c from Contact c ";

	private static final String SELECT_COUNT_FROM_CONTACT_C = "select COUNT(c) from Contact c ";

	@Autowired
	private ContactRepository contactRepo;

	@Autowired
	private CompanyRepository companyRepo;

	@PersistenceContext
	private EntityManager em;

	/**
	 * Get a contact by id
	 * 
	 * @param id
	 *            is ID of the contact we will get.
	 * @return a contact if found and null if not found.
	 */
	@Transactional
	public Contact getContact(int id) {
		return contactRepo.findOne(id);
	}

	@Transactional
	public Contact saveContact(@Valid Contact contact) {

		if (contact == null) {
			return null;
		}

		Work work = contact.getWork();

		if (work != null) {
			Integer companyId = contact.getWork().getCompanyId();
			Company company = contact.getWork().getCompany();

			if (companyId != null) {
				Company com = getCompany(companyId);
				if (com == null) {
					return null;
				}
			}
			if (company != null) {
				saveCompany(company);
			}
		}

		return contactRepo.save(contact);
	}

	@Transactional
	public Company getCompany(Integer id) {
		return companyRepo.findOne(id);
	}

	@Transactional
	public List<Company> getAllCompany() {
		return companyRepo.findAll();
	}

	@Transactional
	public Company saveCompany(@Valid Company company) {

		if (company == null) {
			return null;
		}

		return companyRepo.save(company);
	}

	/**
	 * Service form function delete
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public int deleteContacts(Integer... ids) {
		return contactRepo.deleteContacts(ids);
	}

	/**
	 * Set search criteria to HashMap<K, V> , K - column name, V - criteria value
	 * @param criteria
	 * @return 
	 */
	public LinkedHashMap<String, String> setSearchParam(ContactSearchCriteria criteria) {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

		if (!StringUtils.isEmpty(criteria.getName())) {
			params.put(C_FIRST_NAME, criteria.getName());
			params.put(C_MIDDLE_NAME, criteria.getName());
			params.put(C_LAST_NAME, criteria.getName());
		}
		System.out.println("name: " + criteria.getName());

		if (!StringUtils.isEmpty(criteria.getMobile())) {
			params.put(C_MOBILE, criteria.getMobile());
		}

		if (!StringUtils.isEmpty(criteria.getEmail())) {
			params.put(C_EMAIL, criteria.getEmail());
		}

		if (!StringUtils.isEmpty(criteria.getJobTitle())) {
			params.put(C_WORK_TITLE, criteria.getJobTitle());
		}

		if (!StringUtils.isEmpty(criteria.getDepartment())) {
			params.put(C_WORK_DEPARTMENT, criteria.getDepartment());
		}

		if (!StringUtils.isEmpty(criteria.getCompany())) {
			params.put(C_WORK_COMPANY_NAME, criteria.getCompany());
		}
		return params;
	}
	
	/**
	 * Get real page selected from request
	 * @param page
	 * @param total
	 * @param pageSize
	 * @return 0 if page < 1
	 *         maxPage if page > maxPage
	 *         page - 1 if others
	 */
	private int getRealPageSelected(int page, int total, int pageSize) {
		page = page - 1;
		int maxPage = (int) Math.ceil(total / (double) pageSize);
		if (page <= 0 || maxPage <= 0) {
			page = 0;
		} else if (page >= maxPage) {
			page = maxPage - 1;
		}
		return page;
	}

	/**
	 * Search contact with given criterias
	 * 
	 * @param criteria
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, Object> searchContacts(ContactSearchCriteria criteria, int page, int pageSize) {

		StringBuilder queryString = new StringBuilder(SELECT_C_FROM_CONTACT_C);
		StringBuilder queryCountString = new StringBuilder(SELECT_COUNT_FROM_CONTACT_C);

		LinkedHashMap<String, String> paramsCriteria = setSearchParam(criteria);

		Set<String> columnSet = paramsCriteria.keySet();
		int paramPos = 1;
		if (columnSet != null && !columnSet.isEmpty()) {
			StringBuilder whereClause = new StringBuilder("where ");
			String operator = "";
			if (!StringUtils.isEmpty(criteria.getName())) {
				whereClause.append("(");
			}
			for (String column : columnSet) {
				whereClause.append(operator);
				whereClause.append(column);
				if (paramsCriteria.get(column).contains("*")) {
					whereClause.append(" like ?" + paramPos);
				} else {
					whereClause.append(" =?" + paramPos);
				}
				if (!StringUtils.isEmpty(criteria.getName()) && paramPos < 3) {
					operator = " OR ";
					
				} else {
					if (paramPos == 3) {
						whereClause.append(")");
					}
					operator = " AND ";
				}
				paramPos++;
			}
			queryString.append(whereClause);
			queryCountString.append(whereClause);
		}

		TypedQuery<Contact> query = em.createQuery(queryString.toString(), Contact.class);
		TypedQuery<Long> queryCount = em.createQuery(queryCountString.toString(), Long.class);

		paramPos = 1;
		for (String column : columnSet) {
			if (column.equals(C_FIRST_NAME) || column.equals(C_MIDDLE_NAME) || column.equals(C_LAST_NAME) 
					|| column.equals(C_MOBILE) || column.equals(C_EMAIL)
					|| column.equals(C_WORK_TITLE) || column.equals(C_WORK_DEPARTMENT)
					|| column.equals(C_WORK_COMPANY_NAME)) {
				query.setParameter(paramPos, paramsCriteria.get(column).replace("*", "%"));
				queryCount.setParameter(paramPos, paramsCriteria.get(column).replace("*", "%"));
			}
			paramPos++;
		}
		
		int realPage = getRealPageSelected(page, queryCount.getSingleResult().intValue(), pageSize);
		query.setFirstResult(realPage * pageSize).setMaxResults((realPage + 1) * pageSize - 1);
		List<Contact> contacts = query.getResultList();
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("searchCriteria", criteria);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("totalItem", queryCount.getSingleResult().intValue());
		result.put("data", contacts);

		return result;
	}
}
