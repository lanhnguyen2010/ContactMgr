package vn.kms.launch.contactmgr.domain.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    public User findByUsername(String username);
    
    public User findByEmail(String email);

    @Query("select c.id from Contact c left join c.work.company where "
            + "exists (from User u left join u.assignedCompanies as companyId where u.id = :userId "
                                + "and c.work.company.id = companyId )")
    public List<Integer> getContactIds(@Param("userId")Integer userId);
}
