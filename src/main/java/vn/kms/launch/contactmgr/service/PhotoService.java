package vn.kms.launch.contactmgr.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.kms.launch.contactmgr.domain.image.Photo;
import vn.kms.launch.contactmgr.repository.PhotoRepository;
import vn.kms.launch.contactmgr.util.PhotoUtil;
import vn.kms.launch.contactmgr.util.SearchResult;

@Service
@Transactional
public class PhotoService {
	private static final Logger LOG = LoggerFactory
			.getLogger(PhotoService.class);

	@Autowired(required = true)
	private PhotoRepository uploadRepository;

	@Autowired
	ResourcesProperties resourceProperties;

	@Transactional
	public Photo getPhotoId(int Id) {
		return uploadRepository.findOne(Id);
	}

	@Transactional
	public Photo uploadImage(int photoId, InputStream in,
			String originalFilename, String contentType) throws Exception {

		String relativePath = String.format("%s", originalFilename);

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

	private String getPathFull(String relativePath) throws IOException {
		ClassPathResource cp = new ClassPathResource("");
		String rootPath = "";

		try {
			rootPath = cp.getURL().getFile()
					+ (StringUtils.isEmpty(resourceProperties.getPath()) ? "/etc/photos"
							: resourceProperties.getPath());

		} catch (Exception e) {

		}
		return String.format("%s/%s", rootPath, relativePath);
	}
}
