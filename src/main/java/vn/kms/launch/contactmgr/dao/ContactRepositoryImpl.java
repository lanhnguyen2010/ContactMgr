package vn.kms.launch.contactmgr.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.Item;
import vn.kms.launch.contactmgr.domain.Itemized;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.ContactRepositoryCustom;
import vn.kms.launch.contactmgr.domain.contact.ContactSearchCriteria;
import vn.kms.launch.contactmgr.domain.user.Role;
import vn.kms.launch.contactmgr.util.SearchResult;

@Repository
public class ContactRepositoryImpl implements ContactRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Itemized> getCompanyNames(Integer userId) {
        if(userId == null){
            return null;
        }
        
        List<Itemized> items = new ArrayList<>();
        Query query = em.createQuery("select c.id, c.name from Company c where exists (from User u left join u.assignedCompanies as companyId"
                + " where u.id = :userId and (u.role = :adminRole or c.id = companyId) )");
        query.setParameter("userId", userId);
        query.setParameter("adminRole", "ADMINISTRATOR");

        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            items.add(new Item(row[0].toString(), row[1].toString()));
        }

        return items;
    }

    @Override
    public SearchResult<Contact> searchByCriteria(
            ContactSearchCriteria criteria, Integer userId, String userRole) {
        Map<String, Object> params = new HashMap<>();
        String baseQuery = buildBaseQuery(criteria, userId, userRole, params);
        Query query;

        // count total Greetings matched search criteria
        query = em.createQuery("select count(c) " + baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        int totalContacts = ((Number) query.getSingleResult()).intValue();

        // get page of Greetings matched search criteria
        query = em.createQuery("select c " + baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        query.setFirstResult((criteria.getPageIndex() - 1)
                * criteria.getPageSize());
        query.setMaxResults(criteria.getPageSize());

        List<Contact> contacts = new ArrayList<Contact>();
        if (!isContainPercenSign(criteria)) {
            contacts = query.getResultList();
        }
        return new SearchResult<>(criteria, contacts, totalContacts);
    }

    private String buildBaseQuery(ContactSearchCriteria criteria,
            Integer userId, String userRole, Map<String, Object> params) {
        StringBuilder jpqlQuery = new StringBuilder(
                "from Contact c left join c.work.company where 1=1 ");
        if (userId != null && !StringUtils.isEmpty(userRole)) {
            
            if (!userRole.equals(Role.ADMINISTRATOR.name())) {
                jpqlQuery
                        .append(" and exists (from User u left join u.assignedCompanies as companyId where u.id = :userId "
                                + "and c.work.company.id = companyId and u.role = :userRole)");
            } else {
                jpqlQuery
                        .append(" and exists (from User u where u.id = :userId and u.role = :userRole)");
            }
            params.put("userId", userId);
            params.put("userRole", userRole);

            if (!StringUtils.isEmpty(criteria.getName())) {
                jpqlQuery
                        .append(" and (c.displayName like :name or c.firstName like :name or "
                                + "c.middleName like :name or c.lastName like :name)");
                params.put("name", replaceWildcards(criteria.getName()));
            }

            if (!StringUtils.isEmpty(criteria.getEmail())) {
                jpqlQuery.append(" and c.email like :email");
                params.put("email", replaceWildcards(criteria.getEmail()));
            }

            if (!StringUtils.isEmpty(criteria.getMobile())) {
                jpqlQuery.append(" and c.mobile like :mobile");
                params.put("mobile", replaceWildcards(criteria.getMobile()));
            }

            if (!StringUtils.isEmpty(criteria.getJobTitle())) {
                jpqlQuery.append(" and c.work.title like :title");
                params.put("title", replaceWildcards(criteria.getJobTitle()));
            }

            if (!StringUtils.isEmpty(criteria.getDepartment())) {
                jpqlQuery.append(" and c.work.department like :department");
                params.put("department",
                        replaceWildcards(criteria.getDepartment()));
            }

            if (!StringUtils.isEmpty(criteria.getCompany())) {
                jpqlQuery.append(" and c.work.company.name = :company");
                params.put("company", criteria.getCompany());
            }
        } else {
            // don't search contact if the userId or userRole is null
            jpqlQuery.append(" and 1 = 0");
        }

        return jpqlQuery.toString();
    }

    private boolean isContainPercenSign(ContactSearchCriteria criteria) {
        if (criteria.getName().contains("%")
                || criteria.getEmail().contains("%")
                || criteria.getJobTitle().contains("%")
                || criteria.getMobile().contains("%")
                || criteria.getDepartment().contains("%"))
            return true;
        return false;
    }

    private String replaceWildcards(String text) {
        return text.replace('*', '%');
    }
}
