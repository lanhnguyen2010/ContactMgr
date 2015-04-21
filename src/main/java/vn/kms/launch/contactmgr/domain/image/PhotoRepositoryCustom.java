package vn.kms.launch.contactmgr.domain.image;

import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.util.SearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;

@Repository
public interface PhotoRepositoryCustom {
    SearchResult<Photo> searchByCriteria(SearchCriteria criteria);
}
