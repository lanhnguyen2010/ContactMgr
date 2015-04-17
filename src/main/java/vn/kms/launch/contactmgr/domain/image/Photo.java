package vn.kms.launch.contactmgr.domain.image;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.noggit.JSONUtil;


import vn.kms.launch.contactmgr.domain.Entity;

/**
 * Created by diule on April, 14;
 */
@javax.persistence.Entity
@Table(name = "IMAGES")
public class Photo {

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "PATH_FULL")
    private String pathFull;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CONTENT_TYPE")
    private String ContentType;


    public int getId() {
        return id;
    }

    public void setId(int photoId) {
        this.id = photoId;
    }

    public String getPathFull(String pathFull) {
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

    public String toJson() {
        return JSONUtil.toJSON(this);
    }

    public Photo toDo() {

        Photo res = new Photo();
        res.setId(id);
        res.setFileName(fileName);
        res.setPathFull(pathFull);
        res.setContentType(ContentType);

        return res;
    }

    public Photo() {

    }

    //GET object;
    public Photo(Photo res) {
        id = res.getId();
        fileName = res.fileName;
        ContentType = res.ContentType;
        pathFull = res.pathFull;
    }
}
