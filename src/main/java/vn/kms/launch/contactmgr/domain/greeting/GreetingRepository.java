package vn.kms.launch.contactmgr.domain.greeting;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by trungnguyen on 4/8/15.
 */
@Repository
public interface GreetingRepository extends CrudRepository<Greeting, Integer> {
    Greeting findByCode(String code);

    int deleteByCode(String code);
}
