package vn.kms.launch.contactmgr.domain.greeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreetingRepository extends JpaRepository<Greeting, Integer>, GreetingRepositoryCustom {
    Greeting findByCode(String code);

    int deleteByCode(String code);
}
