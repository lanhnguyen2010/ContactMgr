package vn.kms.launch.contactmgr.domain.image;
import javax.persistence.Column;
import javax.persistence.Table;

import org.noggit.JSONUtil;

import com.fasterxml.jackson.databind.JsonNode;

import vn.kms.launch.contactmgr.domain.Entity;

/**
 * Created by diule on April, 14;
 */
@javax.persistence.Entity
@Table(name = "IMAGE")
public class Photo {
	private static final long serialVersionUID = 1L;

    @Column(name = "ID", insertable = false, updatable = false)
    private String id;

	@Column(name = "PATH_FULL")
	private String pathFull;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "CONTENT_TYPE")
	private String ContentType;
	

	public String getId() {
		return id;
	}

	public void setId(String photoId) {
		this.id = photoId;
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
	
	public String toJson(){
		return JSONUtil.toJSON(this);
	}
	
	public Photo toDo(){
		
		Photo res = new Photo();
		res.setId(id);
		res.setFileName(fileName);
		res.setPathFull(pathFull);
		res.setContentType(ContentType);
		
		return res;	
	}
	public Photo(){
		
	}
	//GET object;
	public Photo(Photo res){
		id = res.getId();
		fileName = res.fileName;
		ContentType = res.ContentType;
		pathFull = res.pathFull;
		
	}
}
