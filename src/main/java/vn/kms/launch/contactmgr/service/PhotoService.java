package vn.kms.launch.contactmgr.service;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtil;
import vn.kms.launch.contactmgr.util.SearchResult;

@Service
@Transactional
public class PhotoService {
        
        @Autowired
	// @Autowired(required = true)
        private PhotoRepository uploadRepository;

	// @Autowired(required = true)
	// private Photo photo;
	@Value("resources.path")
	private String path;
        @Transactional
        public Photo getPhotoId(int Id){
            return uploadRepository.findOne(Id);
        }


        @Transactional
        public Photo uploadImage(@PathVariable("photoId") int photoId,
                                  InputStream in,
                                  String originalFilename,
                                  String contentType) throws Exception {
            
            String relativePath = String.format("%s/%s", photoId, photoId +".png");

            String pathFull = getPathFull(relativePath);

            PhotoUtil.storeFile(in, pathFull);

            Photo res = new Photo();

            res.setId(photoId);
            res.setFileName(originalFilename);
            res.setContentType(contentType);
            res.setPathFull(relativePath);
            
            return uploadRepository.saveAndFlush(res.toDo());
        }

        @Transactional
        public SearchResult<Photo> getListPhotos(int page, int pageSize) {
            List<Photo> result = uploadRepository.findAll();
            int totalPhotos = result.size();
            int toIndex = (pageSize * page) -1;
            
            if( toIndex > totalPhotos){
                toIndex = totalPhotos;
            }
            List<Photo> photos = result.subList((page - 1) * pageSize, toIndex);
            SearchResult<Photo> returnPhotos = new SearchResult<Photo>(null, photos, totalPhotos);
            
            return returnPhotos;
	}
        
        //Path an images to store;
        String path = ("../../../photos");
	// String path = Constants.RESOURCE_PATH

//	//StringBuilder path = new StringBuilder(
//			"D:/Project/Challengs/launch-contact-manager/etc/photos");

        private String getPathFull(String relativePath){
            return String.format("%s/%s",path, relativePath);
        }
}
