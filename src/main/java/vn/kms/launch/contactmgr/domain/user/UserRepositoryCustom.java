package vn.kms.launch.contactmgr.domain.user;


import vn.kms.launch.contactmgr.util.SearchResult;

public interface UserRepositoryCustom {

    public SearchResult<User> searchByCriteria(UserSearchCriteria criteria);
}
