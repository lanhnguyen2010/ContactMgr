package vn.kms.launch.contactmgr.domain.user;


import vn.kms.launch.contactmgr.util.SearchResult;

public interface UserRepositoryCustom {

    public SearchResult<User> searchByCriteria(UserSearchCriteria criteria);

    public int activeUser(int... ids);

    public int deactiveUser(int... ids);

    public int deleteUsers(int... ids);

    public int updateUserAssignedCompanies(int userId);

    public int updateLanguage(String username, String language);

    public int updatePassword(String username, String password);

    public User findByUsername(String username);

}
