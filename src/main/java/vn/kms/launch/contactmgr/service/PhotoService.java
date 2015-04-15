package vn.kms.launch.contactmgr.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.repository.PhotoRepository;


@Service
@Transactional
public class PhotoService {
	
	// Upload image;
		@Autowired
		private PhotoRepository uploadRepository;
		
		@Transactional
		public Photo uploadImage(InputStream in, String fileName,
				String contentType) {
			
			
			 //@Value("${contact.photo.storage}")
			  String photoDir;

			// File uploadFile = File.createTempFile("photo-", photoId);
			// file.transferTo(uploadFile);
			//
			// BufferedImage originalImage = ImageIO.read(uploadFile);
			// // BufferedImage resizedImage = scaleImage(originalImage);
			//
			 //File photoFile = new File(photoDir, photoId + "." + EXT_NAME);
			// ImageIO.write(resizedImage, EXT_NAME, photoFile);
			
			//store file
			
			//push db
			
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
