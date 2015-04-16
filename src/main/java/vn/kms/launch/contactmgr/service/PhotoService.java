package vn.kms.launch.contactmgr.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtil;


@Service
@Transactional
public class PhotoService{

    private PhotoRepository uploadRepository;

    @Transactional
    public Photo getFile(int photoId){
        return uploadRepository.findOne(photoId);
    }

    @Transactional
    public Photo uploadImage( int photoId,
                              InputStream in,
                              String originalFilename, 
                              String contentType) throws Exception {

        String relativePath = String.format("%s/%s", photoId, UUID.randomUUID().toString());
        String pathFull = getPathFull(relativePath);
        
        PhotoUtil.storeFile(in, pathFull);
        Photo res = new Photo();

        res.setId(photoId);
        res.setFileName(originalFilename);
        res.setContentType(contentType);
        res.setPathFull(relativePath);

        return uploadRepository.saveAndFlush(res);
    }

    @Transactional
    public List<Photo> getAllPhoto(int photoId) {

        List<Photo> list = uploadRepository.findAll();
        return list;
    }

    private String getPathFull(String relativePath){
        return String.format("%s/%s", relativePath);
    }

}
