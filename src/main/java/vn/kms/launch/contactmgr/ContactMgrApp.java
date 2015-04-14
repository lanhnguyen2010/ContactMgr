package vn.kms.launch.contactmgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class ContactMgrApp {

	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ContactMgrApp.class);
		
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
