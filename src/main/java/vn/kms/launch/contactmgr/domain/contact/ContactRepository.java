package vn.kms.launch.contactmgr.domain.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.contact.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>, ContactRepositoryCustom {

    @Modifying
    @Query("delete from Contact where id in (:ids)")
    int deleteByIds(@Param("ids") int... ids);
}
