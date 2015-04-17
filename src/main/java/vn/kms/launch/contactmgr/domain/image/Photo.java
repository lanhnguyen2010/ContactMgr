package vn.kms.launch.contactmgr.domain.image;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.noggit.JSONUtil;


import vn.kms.launch.contactmgr.domain.Entity;

/**
 * Created by diule on April, 14;
 */
@JsonInclude
@javax.persistence.Entity
@Table(name = "IMAGES")
public class Photo {

    @Id
    @Column(name = "ID", insertable = false, updatable = false)
    private int id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "RELATIVE_PATH")
    private String relativePath;

    @Column(name = "CONTENT_TYPE")
    private String contentType;

    

    public int getId() {
        return id;
    }

    public void setId(int photoId) {
        this.id = photoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String toJson() {
        return JSONUtil.toJSON(this);
    }

    public Photo toDo() {

        Photo res = new Photo();
        res.setId(id);
        res.setFileName(fileName);
        res.setRelativePath(relativePath);
        res.setContentType(contentType);

        return res;
    }

    public Photo() {

    }

    //GET object;
    public Photo(Photo res) {
        id = res.getId();
        fileName = res.fileName;
        contentType = res.contentType;
        relativePath = res.relativePath;
    }
}
