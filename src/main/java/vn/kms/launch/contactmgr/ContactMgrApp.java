package vn.kms.launch.contactmgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
@EnableTransactionManagement
public class ContactMgrApp {

    public static void main(String[] args) {
    	SpringApplication.run(ContactMgrApp.class, args);
    }

}