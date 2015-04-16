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
<<<<<<< HEAD
import vn.kms.launch.contactmgr.domain.search.ContactSearchCriteria;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;
import vn.kms.launch.contactmgr.repository.PhotoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.zookeeper.server.quorum.ReadOnlyBean;
import org.hibernate.jpa.criteria.path.SetAttributeJoin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

=======
>>>>>>> ca9f332ad93f74520598c6339412620b7fee9bd9
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.utils.PhotoUtils;


@Service
@Transactional
public class PhotoService{

	// Upload image;
		//@Autowired(required = true)
		private PhotoRepository uploadRepository;
<<<<<<< HEAD
		

		// ext_name image allow upload format png, jpeg
		//private static final String EXT_NAME[] = { "png", "jpeg" };
=======
>>>>>>> ca9f332ad93f74520598c6339412620b7fee9bd9

		//@Autowired(required = true)
		//private Photo photo;

<<<<<<< HEAD
		
	
		@Transactional
		public Photo uploadImage(InputStream in, String fileName, String contentType) {
			
			String photoDir;
			Photo ci = new  Photo();
			
			return uploadRepository.save(ci);
=======
		@Transactional
		public Photo getPhotoId(int photoId){
			return uploadRepository.findOne(photoId);
>>>>>>> ca9f332ad93f74520598c6339412620b7fee9bd9
		}

//		@Transactional
//		public List<Photo> getAllPhotoId(){
//			return uploadRepository.findAll();
//		}
		@Transactional
		public Photo uploadImage(@PathVariable String photoId,
								  InputStream in,
								  String originalFilename,
								  String contentType) throws Exception {

			String relativePath = String.format("%s/%s", photoId, UUID.randomUUID().toString());

			String pathFull = getPathFull(relativePath);

			PhotoUtils.storeFile(in, pathFull);

			Photo res = new Photo();

			res.setId(photoId);
			res.setFileName(originalFilename);
			res.setContentType(contentType);
			res.setPathFull(relativePath);
			
			return uploadRepository.saveAndFlush(res.toDo());
		}

		@Transactional
		public List<Photo> getAllPhoto(int photoId) {
			return uploadRepository.findAll();
        }

		private String getPathFull(String relativePath){
			return String.format("%s/%s", relativePath);
		}

}
