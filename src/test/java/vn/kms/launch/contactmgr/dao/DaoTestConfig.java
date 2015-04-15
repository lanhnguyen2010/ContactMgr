package vn.kms.launch.contactmgr.dao;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "vn.kms.launch.contactmgr.domain")
@EntityScan(basePackages = "vn.kms.launch.contactmgr.domain")
@ComponentScan(basePackages = "vn.kms.launch.contactmgr.dao")
public class DaoTestConfig {
}

