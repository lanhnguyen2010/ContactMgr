package vn.kms.launch.contactmgr.service;

import java.io.InputStream;
import java.util.List;
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
    public SearchResult<Photo> getListPhotos(int page, int pageSize) {
        List<Photo> result = uploadRepository.findAll();
        int totalPhotos = result.size();
        int fromIndex = (page - 1) * pageSize + 1;
        int toIndex = page * pageSize;
        
        if (toIndex > totalPhotos) {
            //if OutOfIndex will be returned null
            if (fromIndex > totalPhotos){
            	SearchResult<Photo> returnPhotos = new SearchResult<Photo>(null,null, totalPhotos);
                return returnPhotos;
            }else {
                toIndex = totalPhotos;
            }
        }

        List<Photo> unsortedPhotos = result.subList(fromIndex - 1, toIndex);
        SearchResult<Photo> returnPhotos = new SearchResult<Photo>(null,unsortedPhotos, totalPhotos);
        return returnPhotos;
    }
  
    @Transactional
    public Photo getPhotoById(int Id) {
        return uploadRepository.findOne(Id);
    }

    public String getPathFull(String uuid) {
        return String.format("%s/%s.png", imageDir, uuid);
    }
}
