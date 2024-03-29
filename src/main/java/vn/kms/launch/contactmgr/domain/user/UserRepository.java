package vn.kms.launch.contactmgr.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {

    @Modifying
    @Query("update User u set u.active = 1 where u.id in (:ids)")
    public int activeUser(@Param("ids") int... ids);

    @Modifying
    @Query("update User u set u.active = 0 where u.id in (:ids)")
    public int deactiveUser(@Param("ids") int... ids);

    @Modifying
    @Query("delete from User u where u.id in (:ids)")
    public int deleteUsers(@Param("ids") int... ids);

    @Modifying
    @Query("update User u set u.language = (:language) where username = (:username)")
    public int updateLanguage(@Param("username") String username, @Param("language") String language);

    @Modifying
    @Query("update User u set u.password = (:password) where username = (:username)")
    public int updatePassword(@Param("username") String username, @Param("password") String password);

    @Query("select u from User u where u.username = (:username)")
    public User findByUsername(@Param("username") String username);

    @Modifying
    @Query("update User set password = (:password), resetPasswordFlag = (:isResetPassword) where email = (:email)")
    public int updatePasswordByEmail(@Param("email") String email,
                                     @Param("password") String password,
                                     @Param("isResetPassword") boolean isResetPassword);

    @Query("select u from User u where u.username = (:username) or u.email = (:username)")
    public User findByUsernameOrEmail(@Param("username") String username);

    @Query("select u.password from User u where u.username =:username")
    String getPasswordByUsername(@Param("username") String username);
}
