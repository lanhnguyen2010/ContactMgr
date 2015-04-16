package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.contact.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {


    /**
     * Query function deleteUsers
     * return 0,no row are deleted  
     * @param ids
     * @return
     */
    @Modifying
    @Query("delete from User where id in (:ids)")
    int deleteUsers(@Param("ids")int... ids);
}
