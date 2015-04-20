package vn.kms.launch.contactmgr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.kms.launch.contactmgr.domain.image.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>{
}
