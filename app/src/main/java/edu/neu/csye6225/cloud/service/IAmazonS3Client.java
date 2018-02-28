package edu.neu.csye6225.cloud.service;

import org.springframework.web.multipart.MultipartFile;

public interface IAmazonS3Client {
	public void downloadFile(String keyName);
	public void uploadFile(String keyName, MultipartFile file);
	public void deleteFile(String keyName);
}
