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
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

@Configuration
public class AmazonSNSConfig {
	
	private final String awsId;
	private final String awsKey;
	private final String snsRegion;

	@Autowired
	public AmazonSNSConfig(@Value("${aws.access-key-id}") String awsId,
						  @Value("${aws.access-secret-key}") String awsKey,
						  @Value("${amazon.s3.region}") String snsRegion) {
		this.awsId = awsId;
		this.awsKey = awsKey;
		this.snsRegion = snsRegion;
	}
	
	@Bean
	public AmazonSNS snsClient() {
		
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsId, awsKey);
		AmazonSNSClientBuilder snsClientBuilder = AmazonSNSClientBuilder.standard()
		            .withRegion(Regions.US_EAST_1)
		            .withCredentials(new AWSStaticCredentialsProvider(awsCreds));
		
		return snsClientBuilder.build();
	}
}
