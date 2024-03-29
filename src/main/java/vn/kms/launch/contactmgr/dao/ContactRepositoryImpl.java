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
    public List<Itemized> getCompanyNames(List<Integer> companyIds) {
        Query query = null;
        if (companyIds != null) {
            query = em
                    .createQuery("select c.id, c.name from Company c where c.id in :companyIds");
            query.setParameter("companyIds", companyIds);
        } else {
            // Get all name of companies in database
            query = em.createQuery("select id, name from Company");
        }

        List<Itemized> items = new ArrayList<>();

        List<Object[]> results = query.getResultList();

        for (Object[] row : results) {
            items.add(new Item(row[0].toString(), row[1].toString()));
        }

        return items;
    }

    @Override
    public SearchResult<Contact> searchByCriteria(
            ContactSearchCriteria criteria, List<Integer> companyIds) {
        Map<String, Object> params = new HashMap<>();
        String baseQuery = buildBaseQuery(criteria, companyIds, params);
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
            List<Integer> companyIds, Map<String, Object> params) {
        StringBuilder jpqlQuery = new StringBuilder(
                "from Contact c left join c.work.company where 1=1 ");

        if (companyIds != null) {
            jpqlQuery.append(" and c.work.company.id in :companyIds");
            params.put("companyIds", companyIds);
        }

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
            params.put("department", replaceWildcards(criteria.getDepartment()));
        }

        if (!StringUtils.isEmpty(criteria.getCompany())) {
            jpqlQuery.append(" and c.work.company.name = :company");
            params.put("company", criteria.getCompany());
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
