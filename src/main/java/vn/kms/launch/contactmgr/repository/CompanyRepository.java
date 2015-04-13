package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.kms.launch.contactmgr.domain.contact.Company;

/**
 * Created by thoong on 4/9/2015.
 */
public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
