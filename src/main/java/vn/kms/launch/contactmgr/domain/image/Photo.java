package vn.kms.launch.contactmgr.domain.image;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by diule on April, 14;
 */
@Entity
@Table(name = "IMAGES")
public class Photo {

	@Id
    @Column(name = "ID", insertable = false, updatable = false)
    private Integer id;

    @Column(name = "PATH_FULL")
    private String pathFull;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CONTENT_TYPE")
    private String ContentType;

    @Column(name = "CREATED_AT")
    private Date createdAt;

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

    public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
}
