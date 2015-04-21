package vn.kms.launch.contactmgr.service;

import java.io.InputStream;
import java.util.UUID;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtil;
import vn.kms.launch.contactmgr.util.SearchCriteria;
import vn.kms.launch.contactmgr.util.SearchResult;


@Service
@Transactional
public class PhotoService {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);
    
    @Value("${app.image-dir}")
    private  String imageDir;

    @Autowired(required=true)
    private PhotoRepository uploadRepository;
    
    @Autowired(required=true)
    ResourcesProperties resourceProperties;

    @Transactional
    public Photo getPhotoId(int Id) {
        return uploadRepository.findOne(Id);
    }
    @Transactional
    public Photo uploadImage( InputStream in,
                              String originalFilename,
                              String contentType) throws Exception {
         
         String uuid = UUID.randomUUID().toString();
        Photo res = new Photo();
        
        res.setFileName(originalFilename);
        res.setContentType(contentType);
        res.setPathFull(uuid);
        res.setCreatedAt(DateTime.now().toDate());
        res = uploadRepository.saveAndFlush(res);
        
        PhotoUtil.storeFile(in, getPathFull(uuid));
        return res;
    }
    
    @Transactional
    public SearchResult searchPhotos(SearchCriteria criteria) {
        return uploadRepository.searchByCriteria(criteria);
    }
  
    @Transactional
    public Photo getPhotoById(int Id) {
        return uploadRepository.findOne(Id);
    }

    public String getPathFull(String uuid) {
        return String.format("%s/%s.png", imageDir, uuid);
    }
}
