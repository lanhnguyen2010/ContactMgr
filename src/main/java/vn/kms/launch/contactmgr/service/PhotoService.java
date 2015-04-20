package vn.kms.launch.contactmgr.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.domain.image.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtil;
import vn.kms.launch.contactmgr.util.SearchResult;

public class PhotoService {
    private static final Logger LOG = LoggerFactory.getLogger(PhotoService.class);

    @Autowired(required=true)
    private PhotoRepository uploadRepository;
    
    @Autowired(required=true)
    ResourcesProperties resourceProperties;

    @Transactional
    public Photo getPhotoId(int Id) {
        return uploadRepository.findOne(Id);
    }

    @Transactional
    public Photo uploadImage(InputStream in,
                             String originalFilename,
                             String contentType) throws Exception {

        String relativePath = String.format("/etc/photos/%s", originalFilename);

        String pathFull = getPathFull(relativePath);

        PhotoUtil.storeFile(in, pathFull);

        Photo res = new Photo();

        //res.setId(photoId);
        res.setFileName(originalFilename);
        res.setContentType(contentType);
        res.setPathFull(relativePath);
        res.setCreatedAt(DateTime.now().toDate());

        return uploadRepository.saveAndFlush(res.toDo());
    }

    @Transactional
    public SearchResult<Photo> getListPhotos(int page, int pageSize) {
        List<Photo> result = uploadRepository.findAll();
        int totalPhotos = result.size();
        int toIndex = (pageSize * page) - 1;

        if (toIndex > totalPhotos) {
            toIndex = totalPhotos;
        }
        List<Photo> photos = result.subList((page - 1) * pageSize, toIndex);
        SearchResult<Photo> returnPhotos = new SearchResult<Photo>(null,
            photos, totalPhotos);

        return returnPhotos;
    }
    //Path an images to store, follow user, demo fullPath;

    // String path = Constants.RESOURCE_PATH

    private String getPathFull(String relativePath) throws IOException {
        ClassPathResource cp = new ClassPathResource("");
        String rootPath = "";

        try {
            rootPath = cp.getURL().getFile();
        } catch (Exception e) {

        }
        return String.format("%s/%s", rootPath, relativePath);
    }
}
