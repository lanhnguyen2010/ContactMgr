package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.kms.launch.contactmgr.domain.contact.Contact;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Query("select c from Contact c where lower(c.firstName) like :name% "
            + "or lower(c.middleName) like :name% "
            + "or lower(c.lastName) like :name% "
            + "and lower(c.mobile) like :mobile% "
            + "and lower(c.email) like :email% "
            + "and lower(c.work.title) like :jobTitle% "
            + "and lower(c.work.department) like :department% "
            + "and lower(c.work.company.name) like :company% "
            + "order by c.firstName")
    List<Contact> searchContacts(@Param("name") String name, @Param("mobile") String mobile, @Param("email") String email,
                                @Param("jobTitle") String jobTitle, @Param("department") String department,
                                @Param("company") String company);


	/**
	 * Query function deleteContacts
	 * return 0,no row are deleted
	 * @param ids
	 * @return
	 */
	@Modifying
	@Query("delete from Contact where id in (:ids)")
	int deleteContacts(@Param("ids")Integer... ids);
}
