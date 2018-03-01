package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.modal.UserProfile;
import edu.neu.csye6225.cloud.repository.UserProfileRepository;
import edu.neu.csye6225.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UserProfileService implements IUserProfileService{
	
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private Environment environment;
    
    @Value("${amazon.s3.default-bucket}")
	private String bucketName;
    
    @Value("${amazon.s3.url}")
	private String s3Url;

    @Value("${default.image}")
    private String defaultPicUrl;

    @Autowired
	private IAmazonS3Client amazonS3Client;

    @Override
    public UserProfile findUserProfileByEmail(String userEmail) {
        UserProfile userProfile = userRepository.findUserByEmail(userEmail).getUserProfile();
        return userProfile;
    }

    @Override
    public UserProfile updateUserProfilePicUrl(int id, MultipartFile file) {
        User user = userRepository.findUserByUserId(id);
        amazonS3Client.uploadFile(user.getEmail()+".jpg", file);
        user
        .getUserProfile()
        .setProfilePicUrl(s3Url+"/"+bucketName+"/"+user.getEmail()+".jpg");
        UserProfile savedUserProfile = userProfileRepository.save(user.getUserProfile());
        return savedUserProfile;
    }

    @Override
    public UserProfile updateUserProfileAboutMe(int id, String aboutMe) {
        UserProfile userProfile = userRepository.findUserByUserId(id).getUserProfile();
        userProfile.setAboutMe(aboutMe);
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
        return savedUserProfile;
    }

    @Override
    public UserProfile deleteUserProfilePic(int id) {
        User user = userRepository.findUserByUserId(id);
        amazonS3Client.deleteFile(user.getEmail()+".jpg");
        user.getUserProfile().setProfilePicUrl(defaultPicUrl);
        UserProfile savedUserProfile = userProfileRepository.save(user.getUserProfile());
        return savedUserProfile;
    }

    @Override
    public UserProfile findUserProfileById(int userProfileId) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserprofileId(userProfileId);
        return userProfile;
    }

    private String store(MultipartFile file, int userId) {
    	File desPath = new File(environment.getProperty("local.image.path") + "profilepics/" + userId);
        try {
            file.transferTo(desPath);
            Path p = Paths.get(desPath.toString());
            System.out.println(p.toFile().getName());
            return desPath.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

}
