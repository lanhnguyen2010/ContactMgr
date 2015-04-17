package vn.kms.launch.contactmgr.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.utils.PhotoUtils;


@Service
@Transactional
public class PhotoService {

    // Upload image;
    //@Autowired(required = true)
    private PhotoRepository uploadRepository;

    //@Autowired(required = true)
    //private Photo photo;

    @Transactional
    public Photo getPhotoId(int photoId) {
        return uploadRepository.findOne(photoId);
    }

    @Transactional
    public Photo uploadImage(@PathVariable int photoId,
                             InputStream in,
                             String originalFilename,
                             String contentType) throws Exception {

        //, UUID.randomUUID().toString()
        String relativePath = String.format("%s/%s", photoId, photoId + ".png");

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

    //Path an images to store, follow user, demo fullPath;
    String path = ("D:/Project/Challengs/launch-contact-manager/etc/photos");

    private String getPathFull(String relativePath) {
        return String.format("%s/%s", path, relativePath);
    }

}
