package vn.kms.launch.contactmgr.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.service.ContactService;
import vn.kms.launch.contactmgr.service.PhotoService;

/**
 * Created by diule on 4/14/2015.
 */

@Controller
@RequestMapping(value = "/api/photos")
public class PhotoController {

	// ext_name image allow upload format png, jpeg
	private static final String EXT_NAME[] = { "png", "jpeg" };

	private static final String DEFAULT_PHOTO = "contact-photo.png";

	@Value("${contacts.photo.storage}")
	private String photoDir;

	@Autowired
	private MultipartResolver multipartResolver;

	@Autowired
	PhotoService uploadService;
	
	@RequestMapping(value = "/upload", method = POST)
	public ResponseEntity uploadPhoto(HttpServletRequest request) throws IOException {

		MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);

		MultipartFile file = multipartRequest.getFile("file");
		InputStream in = file.getInputStream();
		String fileName = file.getName();
		
		
	
		String contentType = file.getContentType();
		
		FileFilter filter = new FileNameExtensionFilter(contentType, EXT_NAME);
		
		Photo ci = uploadService.uploadImage(in, fileName, contentType);
		return new ResponseEntity<Photo>(ci, HttpStatus.CREATED);
	}

	/*
	 * Show all images on Dialog;
	 * */
	@RequestMapping(method = GET)
	public @ResponseBody ResponseEntity<?> getAllPhoto(@PathVariable("photoId") int photoId,
			WebRequest request){
		
		//ContactImages contactImage = (ContactImages) uploadService.getAllPhotoId();
		//Get all image and show on Dialog to user;
		return new ResponseEntity<Integer>(photoId, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{photoId}", method = GET)
	public void getPhoto(@PathVariable("photoId") int photoId,
			WebRequest request, HttpServletResponse response) throws IOException {
		Photo ci = uploadService.getFile(photoId);
		
		if (ci == null){
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}	
		else{
			response.setStatus(HttpStatus.OK.value());
			//copy input to output stream of response
		}
	}

}
