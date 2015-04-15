package vn.kms.launch.contactmgr.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.domain.contact.Role;
import vn.kms.launch.contactmgr.domain.contact.User;
import vn.kms.launch.contactmgr.domain.search.UserSearchCriteria;
import vn.kms.launch.contactmgr.repository.UserRepository;

@Service
@Transactional(readOnly=true)
public class UserService {
	
	private static final String C_LAST_NAME = "c.lastName";
	
	private static final String C_FIRST_NAME = "c.firstName";

	private static final String C_EMAIL = "c.email";
	
	private static final String C_ROLE = "c.role";

	private static final String SELECT_C_FROM_USER_C = "select c from User c ";

	private static final String SELECT_COUNT_FROM_USER_C = "select COUNT(c) from User c ";
	
	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Transactional
	public User getUser(int id){
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User saveUser(@Valid User user){
		if(null == user) {
			return null;
		}
		return userRepository.save(user);
	}
	
	@Transactional
	public User updateUser(@Valid User oldUser){
		if(null ==  oldUser){
			return null;
		}
		return userRepository.save(oldUser);
	}
	
	/**
	 * Service form function delete
	 * 
	 * @param ids
	 * @return
	 */
	@Transactional
	public int deleteUsers(int... ids) {
		return userRepository.deleteUsers(ids);
	}
	
	@Transactional
	public User activeUser(int id) {
		User user = getUser(id);
		user.setActive(1);
		return userRepository.save(user);
	}

	@Transactional
	public User deactiveUser(int id) {
		User user = getUser(id);
		user.setActive(0);
		return userRepository.save(user);
	}
	
	/**
	 * Search User with given criteria
	 * 
	 * @param criteria
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public HashMap<String, Object> searchUsers(UserSearchCriteria criteria, int page, int pageSize) {

		StringBuilder queryString = new StringBuilder(SELECT_C_FROM_USER_C);
		StringBuilder queryCountString = new StringBuilder(SELECT_COUNT_FROM_USER_C);

		LinkedHashMap<String, String> paramsCriteria = setSearchParam(criteria);

		StringBuilder whereClause = constructWhereClause(paramsCriteria);
		queryString.append(whereClause);
		queryCountString.append(whereClause);

		TypedQuery<User> query = em.createQuery(queryString.toString(), User.class);
		TypedQuery<Long> queryCount = em.createQuery(queryCountString.toString(), Long.class);

		int paramPos = 1;
		for (String column : paramsCriteria.keySet()) {
			if (column.compareToIgnoreCase(C_ROLE) == 0){
				query.setParameter(paramPos, Role.valueOf(paramsCriteria.get(column)));
				queryCount.setParameter(paramPos, Role.valueOf(paramsCriteria.get(column)));
			} else {
				query.setParameter(paramPos, paramsCriteria.get(column).replace("*", "%"));
				queryCount.setParameter(paramPos, paramsCriteria.get(column).replace("*", "%"));
			}
			paramPos++;
		}
		
		int realPage = getRealPageSelected(page, queryCount.getSingleResult().intValue(), pageSize);
		query.setFirstResult(realPage * pageSize).setMaxResults((realPage + 1) * pageSize);
		List<User> users = query.getResultList();
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("searchCriteria", criteria);
		result.put("page", page);
		result.put("pageSize", pageSize);
		result.put("totalItem", queryCount.getSingleResult().intValue());
		result.put("data", users);

		return result;
	}

	private StringBuilder constructWhereClause( LinkedHashMap<String, String> paramsCriteria) {
		
		Set<String> columnSet = paramsCriteria.keySet();
		StringBuilder whereClause = new StringBuilder("");
		
		if (columnSet != null && !columnSet.isEmpty()) {
			int paramPos = 1;
			whereClause.append("where ");
			
			String operator = "";
			for (String column : columnSet) {
				whereClause.append(operator);
				whereClause.append(column);
				if (paramsCriteria.get(column).contains("*")) {
					whereClause.append(" like ?" + paramPos);
				} else {
					whereClause.append(" =?" + paramPos);
				}
				operator = "AND";
				paramPos++;
			}
		}
		return whereClause;
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
	 * Set search criteria to HashMap<K, V> , K - column name, V - criteria value
	 * @param criteria
	 * @return 
	 */
	private LinkedHashMap<String, String> setSearchParam(UserSearchCriteria criteria) {
		LinkedHashMap<String, String> params = new LinkedHashMap<String, String>();

		if (!StringUtils.isEmpty(criteria.getFirstName())) {
			params.put(C_FIRST_NAME, criteria.getFirstName());
		}
		
		if (!StringUtils.isEmpty(criteria.getLastName())) {
			params.put(C_LAST_NAME, criteria.getLastName());
		}
		
		if (!StringUtils.isEmpty(criteria.getEmail())) {
			params.put(C_EMAIL, criteria.getEmail());
		}

		if (!StringUtils.isEmpty(criteria.getRole())) {
			params.put(C_ROLE, criteria.getRole());
		}

		return params;
	}

}
