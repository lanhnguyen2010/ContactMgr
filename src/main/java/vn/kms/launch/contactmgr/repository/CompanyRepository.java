package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kms.launch.contactmgr.domain.contact.Company;

public interface CompanyRepository  extends JpaRepository<Company, Integer>{

}
