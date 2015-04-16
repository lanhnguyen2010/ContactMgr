package vn.kms.launch.contactmgr.domain.contact;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.kms.launch.contactmgr.domain.Country;

public interface CountryRepository extends JpaRepository<Country, String>{

}
