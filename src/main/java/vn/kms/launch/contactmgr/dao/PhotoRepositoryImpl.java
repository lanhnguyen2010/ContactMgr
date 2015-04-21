package vn.kms.launch.contactmgr.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.domain.image.PhotoRepositoryCustom;
import vn.kms.launch.contactmgr.util.SearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

@Repository
public class PhotoRepositoryImpl implements PhotoRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public SearchResult<Photo> searchByCriteria(SearchCriteria criteria) {
        Map<String, Object> params = new HashMap<>();
        Query query = em.createQuery("select count(p) from Photo p where 1=1 order by p.createdAt desc");

        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        int totalPhotos = ((Number) query.getSingleResult()).intValue();

        // get page of Photos matched search criteria
        query = em.createQuery("select p from Photo p where 1=1 order by p.createdAt desc");
        
        for (String name : params.keySet()) {
            query.setParameter(name, params.get(name));
        }
        query.setFirstResult((criteria.getPageIndex() - 1) * criteria.getPageSize());
        query.setMaxResults(criteria.getPageSize());
        List<Photo> photos = query.getResultList();
        
        return new SearchResult<>(criteria, photos, totalPhotos);
    }
}
