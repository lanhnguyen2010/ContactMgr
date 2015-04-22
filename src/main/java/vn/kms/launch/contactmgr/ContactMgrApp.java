package vn.kms.launch.contactmgr;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableJpaRepositories(basePackages = "vn.kms.launch.contactmgr")
@EnableAutoConfiguration
@SpringBootApplication
@Configuration
@EnableWebMvc
@ComponentScan
@EnableConfigurationProperties
public class ContactMgrApp {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ContactMgrApp.class);
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

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
