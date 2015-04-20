package vn.kms.launch.contactmgr.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import vn.kms.launch.contactmgr.GlobalConfig;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "vn.kms.launch.contactmgr.domain")
@EntityScan(basePackages = "vn.kms.launch.contactmgr.domain")
@ComponentScan(basePackages = {"vn.kms.launch.contactmgr.dao", "vn.kms.launch.contactmgr.service"})
@Import(GlobalConfig.class)
public class ServiceTestConfig {
}
