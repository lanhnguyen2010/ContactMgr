package vn.kms.launch.contactmgr.domain.contact;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>, ContactRepositoryCustom {

    @Modifying
    @Query("delete from Contact where id in (:ids)")
    int deleteByIds(@Param("ids") int... ids);
    
    @Query("select c.id from Contact c left join c.work.company where c.work.company.id in :companyId ")
    public List<Integer> getContactIds(@Param("companyId")List<Integer> companyId);
}