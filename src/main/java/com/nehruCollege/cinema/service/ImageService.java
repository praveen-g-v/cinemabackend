package com.nehruCollege.cinema.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nehruCollege.cinema.exception.ServiceException;
import com.nehruCollege.cinema.model.Image;
import com.nehruCollege.cinema.repository.ImageRepository;

@Service
public class ImageService {
	
	 @Value("${app.file.storage.location}") 
	 private String uploadDir;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private ImageStorageService imageStorageService;
	
	public Image storeImage(MultipartFile file)throws ServiceException{
		try {
			Image image=new Image();
			image.setFilename(file.getOriginalFilename());
	        image.setContentType(file.getContentType());
	        image.setSize(file.getSize());
	        image.setUploadDate(new Date());
	        
	        try {
	        	image.setUploadedFileName(imageStorageService.storeImage(file));
	        }
	        catch(ServiceException e) {
	        	
	        }
	        
	        
	        return imageRepository.save(image);
		}catch(Exception e){
			throw new ServiceException("Unable to Store Image");
		}
	}
	
	public Resource getImage(String id)throws ServiceException {
		Optional<Image> image=Optional.of(imageRepository.findById(id).orElseThrow(()->new ServiceException("Unable to find Image")));
		return imageStorageService.loadImage(image.get().getUploadedFileName());
	}
	
	public Image deleteImage(String id)throws ServiceException{
		Image image=imageRepository.findById(id).orElseThrow(()->{
			return new ServiceException("Unable to find the image");
		});
		imageStorageService.deleteImage(image.getUploadedFileName());
		imageRepository.deleteById(id);
		return image;
	}
	
	

}
