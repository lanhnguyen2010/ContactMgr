package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.service.PhotoService;

/**
 * Created by diule on 4/14/2015.
 */

@Controller
@RequestMapping(value = "/api/photos")
public class PhotoController{
    //private static final String EXT_NAME[] = {"PNG","JPEG"};
    private static final List<MediaType> FILTER_IMAGE = new ArrayList<MediaType>();

    @Autowired
    private MultipartResolver multipartResolver;

    @Autowired
    PhotoService uploadService;
    
    static {
        FILTER_IMAGE.add(MediaType.IMAGE_JPEG);
        FILTER_IMAGE.add(MediaType.IMAGE_PNG);        
    }
    public static Boolean filterUpload(String contentType) {
        
        MediaType type = MediaType.valueOf(contentType);
        return FILTER_IMAGE.contains(type);
    }

    @RequestMapping(value="/upload/{photoId}",method = POST)
    public @ResponseBody ResponseEntity<Photo> uploadPhoto( @PathVariable("photoId") int photoId,
                                               @RequestParam ("fileUpload") MultipartFile file)
                                                throws IOException, ServletException {
        
        Photo res = new Photo();
        
        String contentTpye = file.getContentType();
        if(!filterUpload(contentTpye))
        {
            //System.out.println("You only upload file .PNG or JPEG");
            return new ResponseEntity<Photo>(HttpStatus.PRECONDITION_FAILED);
        }

        try {
            res = uploadService.uploadImage( photoId,
                    file.getInputStream(),
                    file.getOriginalFilename(),
                    contentTpye);
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return new ResponseEntity<Photo>(res, HttpStatus.CREATED);
    }

    /*
     * Show all images on Dialog;
     * */
    @RequestMapping(method = GET)
    public ResponseEntity<Photo> getAllPhoto( @PathVariable("photoId") int photoId,
                                                            HttpServletRequest request,
                                                            HttpServletResponse response) {

        Photo photo = (Photo) uploadService.getAllPhoto(photoId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
     
        List<Photo> list = uploadService.getAllPhoto(photoId);
        if(list.isEmpty()){
            return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Photo>(photo,HttpStatus.OK);

    }

    @RequestMapping(value = "/{photoId}", method = GET)
    public ResponseEntity<Photo> getPhoto( HttpServletResponse response,
                                           @PathVariable("photoId") int photoId) throws IOException {

        Photo res = uploadService.getPhotoId(photoId);
        if (res ==null){
            return new ResponseEntity<Photo>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Photo>(res, HttpStatus.OK);
    }
}
