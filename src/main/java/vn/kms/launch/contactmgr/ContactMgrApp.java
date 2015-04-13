package vn.kms.launch.contactmgr;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vn.kms.launch.contactmgr.domain.contact.Address;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Home;



@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class ContactMgrApp {

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ContactMgrApp.class);
		
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator(); // @Autowire Validator validator;

        Address address = new Address();
        address.setCountry("122");

        Home home = new Home();
        home.setPhone("123");
        home.setAddress(address);

        Contact contact = new Contact();
        contact.setHome(home);

        Set<ConstraintViolation<Contact>> violations = validator.validate(contact);
        
        for (ConstraintViolation<Contact> violation : violations) {
            System.out.printf("Field: %s, message: %s%n", violation.getPropertyPath(), violation.getMessageTemplate());
        }
		
		SimpleCommandLinePropertySource source = new SimpleCommandLinePropertySource(args);
		addDefaultProfile(app, source);

		app.run(args).getEnvironment();
	}

	private static void addDefaultProfile(SpringApplication app,
			SimpleCommandLinePropertySource source) {
		if (!source.containsProperty("spring.profiles.active")) {
			app.setAdditionalProfiles(Constants.PROFILE_DEV);
		}
	}
}
