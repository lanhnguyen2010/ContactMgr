package vn.kms.launch.contactmgr.reposity;
import org.springframework.data.jpa.repository.JpaRepository;

import vn.kms.launch.contactmgr.domain.contact.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
}
