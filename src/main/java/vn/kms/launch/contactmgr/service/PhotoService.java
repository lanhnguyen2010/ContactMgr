package vn.kms.launch.contactmgr.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtils;
import vn.kms.launch.contactmgr.Constants;
import javax.persistence.PersistenceContext;

import org.hibernate.jpa.criteria.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.repository.PhotoRepository;

@Service
@Transactional
public class PhotoService {
	private static final String EXT_NAME = "png";
    // Upload image;
    //@Autowired(required = true)
    private PhotoRepository uploadRepository;
		
		//@Autowired(required = true)
		//private Photo photo;
		
    @Transactional
    public Photo getPhotoId(int photoId) {
			
        return uploadRepository.findOne(photoId);
    }
		
//		@Transactional
//		public List<Photo> getAllPhotoId(){
//			return uploadRepository.findAll();
//		}

    @Transactional
    public Photo uploadImage(@PathVariable int photoId,
                             InputStream in,
                             String originalFilename,
                             String contentType) throws Exception {

        //, UUID.randomUUID().toString()
        String relativePath = String.format("%s", originalFilename);

        String pathFull = getPathFull(relativePath);

        PhotoUtils.storeFile(in, pathFull);

        Photo res = new Photo();

        res.setId(photoId);
        res.setFileName(originalFilename);
        res.setContentType(contentType);
        res.setRelativePath(relativePath);
        return uploadRepository.saveAndFlush(res.toDo());
    }

    @Transactional
    public List<Photo> getAllPhoto(int photoId) {
        return uploadRepository.findAll();
    }

    //Path an images to store, follow user, demo fullPath;
    
   // String path = Constants.RESOURCE_PATH
    
    StringBuilder path = new StringBuilder("D:/Project/Challengs/launch-contact-manager/etc/photos");

    private String getPathFull(String relativePath) {
        return String.format("%s/%s", path, relativePath);
    }
}
