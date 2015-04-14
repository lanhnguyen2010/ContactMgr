package vn.kms.launch.contactmgr.domain.image;
import java.util.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import vn.kms.launch.contactmgr.domain.Entity;
import vn.kms.launch.contactmgr.domain.contact.Home;
import vn.kms.launch.contactmgr.domain.contact.Work;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

/**
 * Created by diule on April, 14;
 */
@javax.persistence.Entity
@Table(name = "contactImages")
public class ContactImages extends Entity {

	//@NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "ID")
    private int id;

	@Column(name = "URL")
	private String url;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	}
