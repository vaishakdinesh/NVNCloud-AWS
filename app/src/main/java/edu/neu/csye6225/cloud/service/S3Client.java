package edu.neu.csye6225.cloud.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

@Service
public class S3Client implements IAmazonS3Client{
	
	private Logger logger = LoggerFactory.getLogger(S3Client.class);
	
	@Autowired
	private AmazonS3 s3client;
 
	@Value("${amazon.s3.default-bucket}")
	private String bucketName;
 
	@Override
	public void downloadFile(String keyName) {
		try {
			
            System.out.println("Downloading an object");
            S3Object s3object = s3client.getObject(new GetObjectRequest(bucketName, keyName));
            System.out.println("Content-Type: "  + s3object.getObjectMetadata().getContentType());
            logger.info("Import File - Done!");
            
        } catch (AmazonServiceException ase) {
        	logger.info("Caught an AmazonServiceException from GET requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }
	}
 
	@Override
	public void uploadFile(String keyName, MultipartFile multipart) {
		try {
			File convFile = new File( multipart.getOriginalFilename());
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
		    fos.write(multipart.getBytes());
		    fos.close();
		    //multipart.transferTo(convFile);
			s3client.putObject(new PutObjectRequest(bucketName, keyName, convFile));
	        logger.info("Upload File - Done!");
	        
		} catch (AmazonServiceException ase) {
			logger.info("Caught an AmazonServiceException from PUT requests, rejected reasons:");
			logger.info("Error Message:    " + ase.getMessage());
			logger.info("HTTP Status Code: " + ase.getStatusCode());
			logger.info("AWS Error Code:   " + ase.getErrorCode());
			logger.info("Error Type:       " + ase.getErrorType());
			logger.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            logger.info("Caught an AmazonClientException: ");
            logger.info("Error Message: " + ace.getMessage());
        }catch (IOException ioe) {
        	logger.info("IOE Error Message: " + ioe.getMessage());
		}
	}

}
