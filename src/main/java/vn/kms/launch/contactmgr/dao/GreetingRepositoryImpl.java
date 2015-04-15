package vn.kms.launch.contactmgr.dao;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.domain.greeting.GreetingRepositoryCustom;
import vn.kms.launch.contactmgr.domain.greeting.GreetingSearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class GreetingRepositoryImpl implements GreetingRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public SearchResult<Greeting> searchByCriteria(GreetingSearchCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        String baseQuery = buildBaseQuery(criteria, params);
        Query query;

        // count total Greetings matched search criteria
        query = em.createQuery("select count(*) " + baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        int totalGreetings = ((Number) query.getSingleResult()).intValue();

        // get page of Greetings matched search criteria
        query = em.createQuery(baseQuery);
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        query.setFirstResult((criteria.getPageIndex() - 1) * criteria.getPageSize());
        query.setMaxResults(criteria.getPageSize());
        List<Greeting> greetings =  query.getResultList();

        return new SearchResult<>(criteria, greetings, totalGreetings);
    }

    private String buildBaseQuery(GreetingSearchCriteria criteria, Map<String, Object> params) {
        StringBuilder jpqlQuery = new StringBuilder("from Greeting where 1=1 ");

        if (!StringUtils.isEmpty(criteria.getCode())) {
            jpqlQuery.append(" and code like :code");
            params.put("code", replaceWildcards(criteria.getCode()));
        }

        if (!StringUtils.isEmpty(criteria.getMessage())) {
            jpqlQuery.append(" and message like :message");
            params.put("message", replaceWildcards(criteria.getMessage()));
        }

        if (criteria.getCreationFrom() != null) {
            jpqlQuery.append(" and createdAt >= :creationFrom");
            params.put("creationFrom", criteria.getCreationFrom());
        }

        if (criteria.getCreationTo() != null) {
            jpqlQuery.append(" and createdAt <= :creationTo");
            params.put("creationTo", criteria.getCreationTo());
        }

        return jpqlQuery.toString();
    }

    private String replaceWildcards(String text) {
        return text.replace('*', '%');
    }
}
