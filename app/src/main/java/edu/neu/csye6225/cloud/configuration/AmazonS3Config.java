package edu.neu.csye6225.cloud.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {

	private final String awsId;
	private final String awsKey;
	private final String s3Region;

	@Autowired
	public AmazonS3Config(@Value("${aws.access-key-id}") String awsId,
						  @Value("${aws.access-secret-key}") String awsKey,
						  @Value("${amazon.s3.region}") String s3Region) {
		this.awsId = awsId;
		this.awsKey = awsKey;
		this.s3Region = s3Region;
	}
 

	


 
	@Bean
	public AmazonS3 s3client() {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
								.withRegion(Regions.fromName(s3Region))
		                        .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
		                        .build();
		
		return s3Client;
	}
}
