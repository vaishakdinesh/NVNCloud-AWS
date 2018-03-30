package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.enums.Role;
import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.modal.UserProfile;
import edu.neu.csye6225.cloud.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import java.util.UUID;

@Service
public class UserService implements IUserService {
	
	private Logger logger = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment env;
    
    @Autowired
    private AmazonSNS snsClient;
    
    @Override
	public User findUserByUserId(int id) {
		User user = userRepository.findUserByUserId(id);
		return user;
	}

	@Override
    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public User saveUser(String fname, String lname, String useremail, String password) {
        User newUser = new User();
        newUser.setFirstName(fname);
        newUser.setLastName(lname);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(useremail);
        newUser.setRole(Role.USER);
        newUser.setToken(issueUniqueToken());
        newUser.setActive(false);
        UserProfile up = new UserProfile();
        up.setProfilePicUrl(env.getProperty("default.image"));
        newUser.setUserProfile(up);
        User user = userRepository.save(newUser);
        return user;
    }


    @Override
    public boolean activateUser(String token) {
        User user = userRepository.findUserByToken(token);
        if(user != null){
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean activateUserJmeter(String email){
        User user = userRepository.findUserByEmail(email);
        if(user != null){
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    private String issueUniqueToken(){
        String token = "";
        do {
            token = getUniqueToken();
        } while (userRepository.findDistinctByToken(token) != null);
        return token;
    }

    private String getUniqueToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }

	@Override
	public void notifySNS(String email) {
		if(env.getActiveProfiles()[0].equals("aws")) email = "0-"+email;
		else email = "1-"+email;
		
		String topicArn="arn:aws:sns:us-east-1:140710200176:forgotPassword";
		PublishRequest publishRequest = new PublishRequest(topicArn, email);
		PublishResult publishResult = snsClient.publish(publishRequest);
		//print MessageId of message published to SNS topic
		logger.info("MessageId - " + publishResult.getMessageId());
	}

	@Override
	public User updatePassword(String email,String password) {
		User user = userRepository.findUserByEmail(email);
		if(user != null){
			user.setPassword(passwordEncoder.encode(password));
			return userRepository.save(user);
		}
			
		return null;
	}
}
