package vn.kms.launch.contactmgr.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.kms.launch.contactmgr.domain.user.User;
import vn.kms.launch.contactmgr.domain.user.UserRepository;

/**
 * Created by thanhtuong on 5/4/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = DaoTestConfig.class)
public class UserRepositoryTest extends TestCase{

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testgetPasswordByUsername() throws Exception {
        User user = new User();
        String password;

        // get with username exist
        user.setUsername("thanhtuong");
        password = userRepository.getPasswordByUsername(user.getUsername());
        assertEquals("Password10@", password);

        // get password with username not exist
        user.setUsername("abc123");
        password=userRepository.getPasswordByUsername(user.getUsername());
        assertEquals(null,password);
    }

    public void testUpdateLanguage() throws Exception{

    }
}
