package vn.kms.launch.contactmgr.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import vn.kms.launch.contactmgr.domain.greeting.Greeting;
import vn.kms.launch.contactmgr.util.ValidationException;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceTestConfig.class)
public class GreetingServiceTest extends TestCase {
    @Autowired
    private GreetingService greetingService;

    @Test
    public void testValidateGreeting() {
        Greeting greeting;

        // both code and message are empty
        greeting = new Greeting("", "");
        try {
            greetingService.validateGreeting(greeting);
            fail();
        } catch (ValidationException e) {
            Map<String, Object> errors = e.getErrors();
            assertThat(errors.size(), is(equalTo(2)));
        }

        // code is in correct format
        greeting = new Greeting("12", "Hello");
        try {
            greetingService.validateGreeting(greeting);
            fail();
        } catch (ValidationException e) {
            Map<String, Object> errors = e.getErrors();
            assertThat(errors.size(), is(equalTo(1)));
            assertThat(errors.get("code").toString(), is(equalTo("must only two letters")));
        }
    }
}
