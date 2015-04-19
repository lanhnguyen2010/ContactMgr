package vn.kms.launch.contactmgr.domain.image;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * Created by diule on April, 14;
 */
@Entity
@Table(name = "IMAGES")
public class Photo {


    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CONTENT_TYPE")
    private String contentType;
    
    @Column(name = "PATH_FULL")
    private String pathFull;


    @Column(name = "CREATED_AT")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public int getId() {
        return id;
    }

    public void setId(int photoId) {
        this.id = photoId;
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
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }


    public Photo toDo() {

        Photo res = new Photo();
        res.setId(id);
        res.setFileName(fileName);
        res.setContentType(contentType);
        res.setPathFull(pathFull);

        return res;
    }

    public Photo() {

    }

    //GET object;
    public Photo(Photo res) {
        id = res.getId();
        fileName = res.fileName;
        contentType = res.contentType;
        pathFull = res.pathFull;
    }
}
