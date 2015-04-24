package vn.kms.launch.contactmgr.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    public User findByUsername(String username);
    
    public User findByEmail(String email);
