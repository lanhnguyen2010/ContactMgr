package vn.kms.launch.contactmgr.dao;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepositoryCustom;
import vn.kms.launch.contactmgr.domain.user.UserSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public SearchResult<User> searchByCriteria(UserSearchCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        String baseQuery = buildBaseQuery(criteria, params);
        Query query = em.createQuery("select count(distinct u) " + baseQuery);

        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        int totalUsers = ((Number) query.getSingleResult()).intValue();

        query = em.createQuery("select distinct u " + baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        query.setFirstResult((criteria.getPageIndex() - 1) * criteria.getPageSize());
        query.setMaxResults(criteria.getPageSize());

        List<User> users = new ArrayList<User>();
        if (!isContainPercenSign(criteria)) {
            users = query.getResultList();
        }

        return new SearchResult<>(criteria, users, totalUsers);

    }

    private String buildBaseQuery(UserSearchCriteria criteria, Map<String, Object> params) {

        StringBuilder jpqlQuery = new StringBuilder("");

        if (!criteria.getAssignedCompanies().isEmpty()) {
            jpqlQuery.append("from User u join u.assignedCompanies ac where ac in :assignCompanies");
            params.put("assignCompanies", criteria.getAssignedCompanies());
        } else {
            jpqlQuery.append("from User u where 1=1");
        }

        if (!StringUtils.isEmpty(criteria.getUsername())) {
            jpqlQuery.append(" and");
            if (criteria.getUsername().contains("*")) {
                jpqlQuery.append(" u.username like :username");
            } else {
                jpqlQuery.append(" u.username = :username");
            }
            params.put("username", replaceWildcards(criteria.getUsername()));
        }
        if (!StringUtils.isEmpty(criteria.getFirstlastName())) {
            jpqlQuery.append(" and");
            if (criteria.getFirstlastName().contains("*")) {
                jpqlQuery.append(" (u.firstname like :name or u.lastname like :name)");
            } else {
                jpqlQuery.append(" (u.firstname = :name or u.lastname = :name)");
            }
            params.put("name", replaceWildcards(criteria.getFirstlastName()));
        }
        if (!StringUtils.isEmpty(criteria.getEmail())) {
            jpqlQuery.append(" and");
            if (criteria.getEmail().contains("*")) {
                jpqlQuery.append(" u.email like :email");
            } else {
                jpqlQuery.append(" u.email = :email");
            }
            params.put("email", replaceWildcards(criteria.getEmail()));
        }
        if (!StringUtils.isEmpty(criteria.getCreatedFrom())) {
            jpqlQuery.append(" and");
            jpqlQuery.append(" u.createdAt >= :created_from");
            params.put("created_from", new Date(criteria.getCreatedFrom().getTime()));
        }
        if (!StringUtils.isEmpty(criteria.getCreatedTo())) {
            jpqlQuery.append(" and");
            jpqlQuery.append(" u.createdAt < :created_to");
            params.put("created_to", new Date(criteria.getCreatedTo().getTime() + (1000L * 60 * 60 * 24)));
        }
        if (!StringUtils.isEmpty(criteria.getRole())) {
            jpqlQuery.append(" and");
            jpqlQuery.append(" u.role like :role");
            params.put("role", criteria.getRole());
        }

        return jpqlQuery.toString();
    }

    private boolean isContainPercenSign(UserSearchCriteria criteria) {
        if (criteria.getEmail().contains("%") || criteria.getFirstlastName().contains("%") || criteria.getUsername().contains("%"))
            return true;
        return false;
    }

    private String replaceWildcards(String text) {
        return text.replace('*', '%');
    }
}
