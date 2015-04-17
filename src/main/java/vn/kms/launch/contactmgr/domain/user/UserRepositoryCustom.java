package vn.kms.launch.contactmgr.domain.user;


import vn.kms.launch.contactmgr.util.SearchResult;

public interface UserRepositoryCustom {
    
    public SearchResult<User> searchByCriteria(UserSearchCriteria criteria);
    
    public Integer activeUser(int... ids);
    
    public Integer deactiveUser(int... ids);

}
