package vn.kms.launch.contactmgr.service;

import junit.framework.TestCase;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.kms.launch.contactmgr.dto.user.ChangePasswordInfo;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceTestConfig.class)
public class UserServiceTest extends TestCase {
    @Autowired
    private UserService userService;

    @BeforeClass
    public static void Login() {
        // do login with sepecify username
    }

    @Test
    public void testUpdateLanguage() {
        int result;
        result = userService.updateLanguage("VI");
        assertThat(result, is(equalTo(1)));

        result = userService.updateLanguage("EN");
        assertThat(result, is(equalTo(1)));

        // null language
        result = userService.updateLanguage("");
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testUpdatePassword() {
        ChangePasswordInfo changePasswordInfo;
        int result;
        //correct all
        changePasswordInfo = new ChangePasswordInfo("Password10@", "Password10@1", "Password10@1");
        result = userService.updatePassword(changePasswordInfo);
        assertThat(result, is(equalTo(1)));
        // passwordOld not correct
        changePasswordInfo = new ChangePasswordInfo("Password1@", "Password10@1", "Password10@1");
        result = userService.updatePassword(changePasswordInfo);
        assertThat(result, is(equalTo(0)));
        // passwordConfirm not match
        changePasswordInfo = new ChangePasswordInfo("password10@", "password10@1", "password10@10");
        result = userService.updatePassword(changePasswordInfo);
        assertThat(result, is(equalTo(0)));
    }

    @Test
    public void testUpdatePasswordByEmail() throws Exception {
    }
}
