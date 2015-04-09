package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vn.kms.launch.contactmgr.domain.contact.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
	@Modifying
	@Query("delete from Contact where id in (:ids)")
	void deleteContacts(@Param("ids") String... ids);

	@Query("from Contact where id = 1")
	public Contact getContact();
}
