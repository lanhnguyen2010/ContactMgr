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

import vn.kms.launch.contactmgr.domain.image.ContactImages;
import vn.kms.launch.contactmgr.repository.UploadRepository;
import vn.kms.launch.contactmgr.service.ContactService;
import vn.kms.launch.contactmgr.service.UploadService;

/**
 * Created by diule on 4/14/2015.
 */

@Controller
@RequestMapping(value = "/api/photo")
public class UploadController {

	// ext_name image allow upload format png, jpeg
	private static final String EXT_NAME[] = { "png", "jpeg" };

	private static final String DEFAULT_PHOTO = "contact-photo.png";

	@Value("${contacts.photo.storage}")
	private String photoDir;

	@Autowired
	private MultipartResolver multipartResolver;

	
	@Autowired
	UploadService uploadService;
	
	/*Upload image WS: an image with format PNG, JPEG
	 * @return ""
	 * if user upload an image illegal format, "HTTP Error 412 - Precondition failed";
	 * */

	@RequestMapping(value="/upload",method = POST)
	public ResponseEntity uploadPhoto(HttpServletRequest request)
			throws IOException {

		MultipartHttpServletRequest multipartRequest = multipartResolver
				.resolveMultipart(request);

		MultipartFile file = multipartRequest.getFile("file");
		InputStream in = file.getInputStream();
		String fileName = file.getName();
	
		String contentType = file.getContentType();
		//TODO: Filter contentType format image;	
		ContactImages ci = uploadService.uploadImage(in, fileName, contentType);
		// TODO:ff
		
		// "url":"/api/";
		return new ResponseEntity<ContactImages>(ci, HttpStatus.CREATED);
		//
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
	
	/*Get photoId return user
	 * @return ""
	 * 
	 * */
	@RequestMapping(value = "/{photoId}", method = GET)
	public void getPhoto(@PathVariable("photoId") int photoId,
			WebRequest request, HttpServletResponse response) throws IOException {
		ContactImages ci = uploadService.getFile(photoId);
		
		if (null == ci)
			response.setStatus(HttpStatus.NOT_FOUND.value());
		else{
		response.setStatus(HttpStatus.OK.value());
		//copy input to output stream of response
		}
//		
//		File photoFile = new File(photoDir, photoId + "." + EXT_NAME);
//		if (!photoFile.exists()) {
//			photoFile = new File(photoDir, DEFAULT_PHOTO);
//		}
//
//		if (request.checkNotModified(photoFile.lastModified())) {
//			return null; // return 304 code
//		}
//
//		byte[] photo = Files.readAllBytes(Paths.get(photoFile.getPath()));
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.IMAGE_PNG);
//		// headers.setContentType(MediaType.IMAGE_JPEG);
//
//		headers.setContentLength(photo.length);
//		headers.setLastModified(photoFile.lastModified());
//		return new HttpEntity<byte[]>(photo, headers);
	}

	// ----------------------------------------------

	private static BufferedImage scaleImage(BufferedImage image, int width,
			int height) throws IOException {
		int type = image.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : image
				.getType();
		int imageWidth = image.getWidth();
		int imageHeight = image.getHeight();

		double scaleX = (double) width / imageWidth;
		double scaleY = (double) height / imageHeight;
		AffineTransform scaleTransform = AffineTransform.getScaleInstance(
				scaleX, scaleY);
		AffineTransformOp bilinearScaleOp = new AffineTransformOp(
				scaleTransform, AffineTransformOp.TYPE_BILINEAR);

		image = bilinearScaleOp.filter(image, new BufferedImage(width, height,
				type));
		return image.getSubimage(0, (height - width) / 2, width, width);
	}

}
