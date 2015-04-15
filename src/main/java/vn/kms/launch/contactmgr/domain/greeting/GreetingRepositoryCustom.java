package vn.kms.launch.contactmgr.domain.greeting;

import vn.kms.launch.contactmgr.util.SearchResult;

public interface GreetingRepositoryCustom {
    SearchResult<Greeting> searchByCriteria(GreetingSearchCriteria criteria);
}
