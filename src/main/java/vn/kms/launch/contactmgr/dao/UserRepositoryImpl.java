package vn.kms.launch.contactmgr.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepositoryCustom;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {
	
    @PersistenceContext
    private EntityManager em;

	@Override
	public SearchResult<User> searchByCriteria(UserSearchCriteria criteria) {
		Map<String, Object> params = new HashMap<>();
        String baseQuery = buildBaseQuery(criteria, params);
        Query query = em.createQuery("select count(u) " + baseQuery);
        
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        int totalUsers = ((Number) query.getSingleResult()).intValue();
        
     // get page of Greetings matched search criteria
        query = em.createQuery("select u " + baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        query.setFirstResult((criteria.getPageIndex() - 1) * criteria.getPageSize());
        query.setMaxResults(criteria.getPageSize());
        List<User> users =  query.getResultList();

        return new SearchResult<>(criteria, users, totalUsers);

	}
	
    private String buildBaseQuery(UserSearchCriteria criteria, Map<String, Object> params) {
    	
        StringBuilder jpqlQuery = new StringBuilder("from User u where 1=1"); 
        
        if (!StringUtils.isEmpty(criteria.getUsername())){
        	jpqlQuery.append(" and");
        	if (criteria.getUsername().contains("*")){
        		jpqlQuery.append(" u.username like :username");
        	} else {
        		jpqlQuery.append(" u.username = :username");
        	}
        	params.put("username", replaceWildcards(criteria.getUsername()));
        }
        if (!StringUtils.isEmpty(criteria.getFirstlastName())){
        	jpqlQuery.append(" and");
        	if (criteria.getFirstlastName().contains("*")){
        		jpqlQuery.append(" (u.firstname like :name or u.lastname like :name)");
        	} else {
        		jpqlQuery.append(" (u.firstname = :name or u.lastname = :name)");
        	}
        	params.put("name", replaceWildcards(criteria.getFirstlastName()));
        }
        if (!StringUtils.isEmpty(criteria.getEmail())){
        	jpqlQuery.append(" and");
        	if (criteria.getEmail().contains("*")){
        		jpqlQuery.append(" u.email like :email");
        	} else {
        		jpqlQuery.append(" u.email = :email");
        	}
        	params.put("email", replaceWildcards(criteria.getEmail()));
        }
        if (!StringUtils.isEmpty(criteria.getCreatedFrom()) && !StringUtils.isEmpty(criteria.getCreatedTo()) ){
        	jpqlQuery.append(" and");
        	jpqlQuery.append(" u.created_at > :created_from and u.created_at < :created_to");
        	params.put("created_from", new SimpleDateFormat("yyyy-mm-dd").format(criteria.getCreatedFrom()));
        	params.put("created_to", new SimpleDateFormat("yyyy-mm-dd").format(criteria.getCreatedTo()));
        }
        if (!StringUtils.isEmpty(criteria.getAssignedCompanies())){
        	jpqlQuery.append(" and");
        	jpqlQuery.append(" u.assigned_companies like :assigned_companies");
        	params.put("assigned_companies", criteria.getAssignedCompanies());
        }
        if (!StringUtils.isEmpty(criteria.getRole())){
        	jpqlQuery.append(" and");
        	jpqlQuery.append(" u.role like :role");
        	params.put("role", criteria.getRole());
        }

        return jpqlQuery.toString();
    }

    private String replaceWildcards(String text) {
        return text.replace('*', '%');
    }

	@Override
	public Integer activeUser(int... ids) {
		List<Integer> lst = new ArrayList<>();
		for (int i : ids){
			lst.add(i);
		}
		Query query = em.createQuery("update User u set u.active=1 where u.id in :ids");
		query.setParameter("ids", lst);
		int result = query.executeUpdate();
		return result;
	}

	@Override
	public Integer deactiveUser(int... ids) {
		List<Integer> lst = new ArrayList<>();
		for (int i : ids){
			lst.add(i);
		}
		Query query = em.createQuery("update User u set u.active=0 where u.id in :ids");
		query.setParameter("ids", lst);
		int result = query.executeUpdate();
		return result;
	}

}
