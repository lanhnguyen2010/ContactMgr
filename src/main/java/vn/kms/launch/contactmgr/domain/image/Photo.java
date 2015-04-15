package vn.kms.launch.contactmgr.domain.image;
import java.util.Date;

import org.apache.http.entity.ContentType;
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
@Table(name = "IMAGE")
public class Photo extends Entity {

	//@NotEmpty(message = "{validation.not-empty.message}")
    @Column(name = "ID")
    private Integer id;

	@Column(name = "PATHFULL")
	private String pathFull;
	
	@Column(name = "FILENAME")
	private String fileName;
	
	@Column(name = "CONTENTTYPE")
	private String ContentType;

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPathFull() {
		return pathFull;
	}

	public void setPathFull(String pathFull) {
		this.pathFull = pathFull;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return ContentType;
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}
}
