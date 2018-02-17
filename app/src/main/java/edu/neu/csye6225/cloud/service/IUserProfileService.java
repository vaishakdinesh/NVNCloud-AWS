package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.UserProfile;
import org.springframework.web.multipart.MultipartFile;

public interface IUserProfileService {
    UserProfile findUserProfileByEmail(String userEmail);
    UserProfile updateUserProfilePicUrl(int id, MultipartFile file);
    UserProfile updateUserProfileAboutMe(int id, String aboutMe);
    UserProfile findUserProfileById(int userProfileId);
}
