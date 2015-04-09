package vn.kms.launch.contactmgr;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import vn.kms.launch.contactmgr.service.ContactService;
@Component
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan
public class ContactMgrApp extends SpringBootServletInitializer{
	
	@Override
	 protected SpringApplicationBuilder configure(SpringApplicationBuilder application){
		return application.sources(ContactMgrApp.class);
	}
	
    public static void main(String...args) throws FileNotFoundException, IOException {
    	ApplicationContext appContext = SpringApplication.run(ContactMgrApp.class, args);
    	ContactService contactService = appContext.getBean(ContactService.class);
        //String filePath = (args.length > 0)? args[0] : "etc/contacts.txt";
        //long index =contactService.loadContacts(filePath);
    	//System.out.println("DJHDG"+ index);
    }

}