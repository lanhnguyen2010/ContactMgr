package vn.kms.launch.contactmgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import vn.kms.launch.contactmgr.service.ContactService;

@SpringBootApplication
public class ContactMgrApp {

    public static void main(String[] args) {
    	 ApplicationContext appContext = SpringApplication.run(ContactMgrApp.class, args);
        ContactService contactService = appContext.getBean(ContactService.class);
    }

}