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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.contact.Company;
import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.contact.Work;
import vn.kms.launch.contactmgr.domain.image.ContactImages;
import vn.kms.launch.contactmgr.domain.search.ContactSearchCriteria;
import vn.kms.launch.contactmgr.repository.CompanyRepository;
import vn.kms.launch.contactmgr.repository.ContactRepository;
import vn.kms.launch.contactmgr.repository.UploadRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.zookeeper.server.quorum.ReadOnlyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.kms.launch.contactmgr.repository.UploadRepository;


@Service
@Transactional
public class UploadService {
	
	// Upload image;
		@Autowired
		private UploadRepository uploadRepository;

		@PersistenceContext
		private EntityManager em;
		
		@Transactional
		public ContactImages uploadImage(InputStream in, String fileName,
				String contentType) {

			// File uploadFile = File.createTempFile("photo-", photoId);
			// file.transferTo(uploadFile);
			//
			// BufferedImage originalImage = ImageIO.read(uploadFile);
			// // BufferedImage resizedImage = scaleImage(originalImage);
			//
			// File photoFile = new File(photoDir, photoId + "." + EXT_NAME);
			// ImageIO.write(resizedImage, EXT_NAME, photoFile);
			
			//store file
			
			//push db
			
			ContactImages ci = new  ContactImages();
			
			return uploadRepository.save(ci);
		}

		@Transactional
		public InputStream getFile(int photoId){
		return null;	
		}
		
		@Transactional
		public List<ContactImages> getAll(){
			return null;
		}

}
