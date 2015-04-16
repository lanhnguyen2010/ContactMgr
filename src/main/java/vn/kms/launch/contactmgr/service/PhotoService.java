package vn.kms.launch.contactmgr.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.domain.image.Photo;
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

import vn.kms.launch.contactmgr.repository.PhotoRepository;


@Service
@Transactional
public class PhotoService {
	
	// Upload image;
		@Autowired
		private PhotoRepository uploadRepository;
		

		// ext_name image allow upload format png, jpeg
		//private static final String EXT_NAME[] = { "png", "jpeg" };

		private static final String DEFAULT_PHOTO = "contact-photo.png";

		
	
		@Transactional
		public Photo uploadImage(InputStream in, String fileName, String contentType) {
			
			String photoDir;
			Photo ci = new  Photo();
			
			return uploadRepository.save(ci);
		}

		@Transactional
		public Photo getFile(int photoId){
			return uploadRepository.findOne(photoId);	
		}
		
		@Transactional
		public List<Photo> getAllPhotoId(){
			return uploadRepository.findAll();
		}

}
