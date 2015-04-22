package vn.kms.launch.contactmgr.domain.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>, PhotoRepositoryCustom {
}
