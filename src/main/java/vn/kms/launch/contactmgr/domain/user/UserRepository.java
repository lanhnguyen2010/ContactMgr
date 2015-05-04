package vn.kms.launch.contactmgr.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    public User findByUsername(String username);

    @Modifying
    @Query("update User set password = (:password), resetPasswordFlag = true where email = (:email)")
    public int resetPasswordByEmail(@Param("email") String email, @Param("password") String password);
}
