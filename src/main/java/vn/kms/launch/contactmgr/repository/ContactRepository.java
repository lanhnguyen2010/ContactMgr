package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.kms.launch.contactmgr.domain.contact.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
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
