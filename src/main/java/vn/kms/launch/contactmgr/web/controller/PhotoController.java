package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import vn.kms.launch.contactmgr.domain.contact.Contact;
import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.service.PhotoService;

/**
 * Created by diule on 4/14/2015.
 */

@Controller
@RequestMapping(value = "/api/photos")
public class PhotoController {
    @Autowired
    private MultipartResolver multipartResolver;

    @Autowired
    PhotoService uploadService;

    @RequestMapping(value="/upload/{photoId}",method = POST)
    public ResponseEntity <Photo> uploadPhoto( @PathVariable("photoId") int photoId,
                                               @RequestParam ("file") MultipartFile file)
        throws IOException {

        Photo res = null;
        FileNameExtensionFilter filterImage;
        filterImage = new FileNameExtensionFilter("file only","PNG","JPEG");
        try {
            res = uploadService.uploadImage( photoId,
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    file.getContentType());
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return new ResponseEntity<Photo>(res, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = GET)
    public ResponseEntity<Photo> getAllPhoto(@PathVariable("photoId") int photoId, HttpServletRequest request,
                                                           HttpServletResponse response) {

        Photo photo = (Photo) uploadService.getAllPhoto(photoId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        List<Photo> list = uploadService.getAllPhoto(photoId);

        return new ResponseEntity<Photo>(photo,HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{photoId}", method = GET)
    public void getPhoto(HttpServletResponse response,
                         @PathVariable("photoId") int photoId) throws IOException {

        response.setStatus(HttpStatus.FOUND.value());

        Photo res = uploadService.getFile(photoId);
        response.setContentType(res.getContentType());
    }
}
