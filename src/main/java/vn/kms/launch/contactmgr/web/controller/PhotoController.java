package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;	
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.service.PhotoService;
import vn.kms.launch.contactmgr.util.SearchResult;

/**
 * Created by diule on 4/14/2015.
 */

@Controller
@RequestMapping(value = "/api/photos/")
public class PhotoController {
//	@Autowired
//	//@Value("${contacts.photo.storage}")
//	private String photoDir;

    private static final List<MediaType> FILTER_IMAGE = new ArrayList<MediaType>();

    @Autowired(required=true)
    private MultipartResolver multipartResolver;

    @Autowired (required=true)
    PhotoService uploadService;

    static {
        FILTER_IMAGE.add(MediaType.IMAGE_JPEG);
        FILTER_IMAGE.add(MediaType.IMAGE_PNG);
    }

    public static Boolean filterUpload(String contentType) {

        MediaType type = MediaType.valueOf(contentType);
        return FILTER_IMAGE.contains(type);
    }

    @RequestMapping(value = "/upload/", method = POST)
    public @ResponseBody  ResponseEntity<Photo> uploadPhoto( @RequestBody MultipartFile file)
                                                            throws IOException, ServletException {

        Photo res = new Photo();
        String contentTpye = file.getContentType();
        if (!filterUpload(contentTpye)) {
            return new ResponseEntity<Photo>(HttpStatus.PRECONDITION_FAILED);
        }

        try {
            res = uploadService.uploadImage(
                file.getInputStream(),
                file.getOriginalFilename(),
                contentTpye);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<Photo>(res, HttpStatus.CREATED);
    }

    @RequestMapping(method = GET)
    public ResponseEntity<SearchResult<Photo>> getListPhotos( @RequestParam(value = "pageIndex", defaultValue = "1") String pageIndex, @RequestParam(value = "pageSize", defaultValue = "10") String pageSize) {

        SearchResult<Photo> list = uploadService.getListPhotos(Integer.parseInt(pageIndex), Integer.parseInt(pageSize));
        if (list.getTotalItems() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @RequestMapping(value="/{uuid}", method = GET)
    public HttpEntity<byte[]> getPhoto(@PathVariable String uuid, WebRequest request) throws IOException {
    	
    	File photoFile = new File(uploadService.getPathFull(uuid));
        
        if (request.checkNotModified(photoFile.lastModified())) {
            return null; // return 304 code
        }
        
        byte[] bytes = Files.readAllBytes(Paths.get(photoFile.getPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(bytes.length);
        headers.setLastModified(photoFile.lastModified());
        return new HttpEntity<byte[]>(bytes, headers);
    }
}
