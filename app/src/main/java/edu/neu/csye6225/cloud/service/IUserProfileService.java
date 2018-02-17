package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.UserProfile;

public interface IUserProfileService {
    UserProfile findUserProfileByEmail(String userEmail);
    UserProfile updateUserProfilePicUrl(String userEmail, String proPicUrl);
    UserProfile updateUserProfileAboutMe(int id, String aboutMe);
    UserProfile findUserProfileById(int userProfileId);
}
