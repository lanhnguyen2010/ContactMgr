package vn.kms.launch.contactmgr.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.hibernate.hql.internal.classic.WhereParser;
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
	
	private static final String USERNAME = "c.userName";
	
	private static final String FIRST_NAME = "c.firstName";
	
	private static final String LAST_NAME = "c.lastName";

	private static final String EMAIL = "c.email";
	
	private static final String ROLE = "c.role";

	private static final String SELECT_C_FROM_USER_C = "select c from User c ";

	private static final String SELECT_COUNT_FROM_USER_C = "select COUNT(c) from User c ";

	private static final String ASSIGNED_COMPANY = "c.assignedcompanies";

	private static final String CREATED_FROM = "c.created_at";

	private static final String CREATED_TO = "c.created_at";
	
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
	public List<Integer> activeUser(int... ids) {
		
		List<Integer> deletedIds = new ArrayList<Integer>();
		for (int id: ids){
			User user = getUser(id);
			if (user != null){
				user.setActive(1);
				if (userRepository.save(user) != null){
					deletedIds.add(id);
				}	
			}
		}
		return deletedIds;
	}

	@Transactional
	public List<Integer> deactiveUser(int... ids) {
		List<Integer> deletedIds = new ArrayList<Integer>();
		for (int id: ids){
			User user = getUser(id);
			if (user != null){
				user.setActive(0);
				if (userRepository.save(user) != null){
					deletedIds.add(id);
				}	
			}
		}
		return deletedIds;
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
			if (column.equalsIgnoreCase(ROLE)){
				query.setParameter(paramPos, Role.valueOf(paramsCriteria.get(column)));
				queryCount.setParameter(paramPos, Role.valueOf(paramsCriteria.get(column)));
			} else if (column.equalsIgnoreCase(ASSIGNED_COMPANY)) {
				String value = "%" + paramsCriteria.get(column) + "%";
				query.setParameter(paramPos, value);
				queryCount.setParameter(paramPos, value);
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
			
			boolean isFristLastNameInserted = false;
			for (String column : columnSet) {
				whereClause.append(operator);
				if (column.equalsIgnoreCase(FIRST_NAME) || column.equalsIgnoreCase(LAST_NAME)){
					if (isFristLastNameInserted) continue;
					StringBuilder sb = new StringBuilder();
					sb.append("( ");
					sb.append(FIRST_NAME).append(" ");
					sb.append(constructLikeOrEqualQuery(FIRST_NAME, paramsCriteria.get(FIRST_NAME), paramPos++));
					sb.append(" OR ");
					sb.append(LAST_NAME).append(" ");
					sb.append(constructLikeOrEqualQuery(LAST_NAME, paramsCriteria.get(LAST_NAME), paramPos));
					sb.append(" )");
					isFristLastNameInserted = true;
					whereClause.append(sb);
					continue;
				} else {
					whereClause.append(column);
				}
				
				whereClause.append(" ").append(constructLikeOrEqualQuery(column, paramsCriteria.get(column), paramPos));
				operator = "AND";
				paramPos++;
			}
		}
		return whereClause;
	}
	
	private String constructLikeOrEqualQuery(String column, String value, int paramPos){
		String result = "";
		
		if (column.equalsIgnoreCase(ASSIGNED_COMPANY)){
			result = "like ?" + paramPos;
			return result;
		}
		
		if (value.contains("*")){
			result = "like ?" + paramPos;
		} else {
			result = "=?" + paramPos;
		}
		return result;
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

		if (!StringUtils.isEmpty(criteria.getUserName())) {
			params.put(USERNAME, criteria.getUserName());
		}
		
		if (!StringUtils.isEmpty(criteria.getFirstlastName())) {
			params.put(FIRST_NAME, criteria.getFirstlastName());
			params.put(LAST_NAME, criteria.getFirstlastName());
		}
		
		if (!StringUtils.isEmpty(criteria.getEmail())) {
			params.put(EMAIL, criteria.getEmail());
		}

		if (!StringUtils.isEmpty(criteria.getRole())) {
			params.put(ROLE, criteria.getRole());
		}
		
		if (!StringUtils.isEmpty(criteria.getAssignedCompanies())) {
			params.put(ASSIGNED_COMPANY, criteria.getAssignedCompanies());
		}
		
		if (!StringUtils.isEmpty(criteria.getCreatedFrom())) {
			params.put(CREATED_FROM, criteria.getCreatedFrom());
		}
		
		if (!StringUtils.isEmpty(criteria.getCreatedTo())) {
			params.put(CREATED_TO, criteria.getCreatedTo());
		}

		return params;
	}


}
