package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.modal.UserProfile;
import edu.neu.csye6225.cloud.repository.UserProfileRepository;
import edu.neu.csye6225.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService implements IUserProfileService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Override
    public UserProfile findUserProfileByEmail(String userEmail) {
        UserProfile userProfile = userRepository.findUserByEmail(userEmail).getUserProfile();
        return userProfile;
    }

    @Override
    public UserProfile updateUserProfilePicUrl(String userEmail, String proPicUrl) {
        UserProfile userProfile = userRepository.findUserByEmail(userEmail).getUserProfile();
        userProfile.setProfilePicUrl(proPicUrl);
        UserProfile savedUserProfile = userProfileRepository.save(userProfile);
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
    public UserProfile findUserProfileById(int userProfileId) {
        UserProfile userProfile = userProfileRepository.findUserProfileByUserprofileId(userProfileId);
        return userProfile;
    }
}
