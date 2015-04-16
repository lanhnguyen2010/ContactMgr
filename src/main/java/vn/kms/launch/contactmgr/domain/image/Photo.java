package vn.kms.launch.contactmgr.domain.image;
import javax.persistence.Column;
import javax.persistence.Table;

import vn.kms.launch.contactmgr.domain.Entity;

/**
 * Created by diule on April, 14;
 */
@javax.persistence.Entity
@Table(name = "IMAGE")
public class Photo extends Entity {
	private static final long serialVersionUID = 1L;

    @Column(name = "ID", insertable = false, updatable = false)
    private Integer id;

	@Column(name = "PATH_FULL")
	private String pathFull;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "CONTENT_TYPE")
	private String ContentType;
	

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPathFull( String pathFull) {
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
