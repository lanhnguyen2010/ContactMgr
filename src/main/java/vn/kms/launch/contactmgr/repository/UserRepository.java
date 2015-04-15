package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.contact.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
}
