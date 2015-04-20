package vn.kms.launch.contactmgr.domain.contact;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.contact.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
