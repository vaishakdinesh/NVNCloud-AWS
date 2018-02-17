package edu.neu.csye6225.cloud.repository;

import edu.neu.csye6225.cloud.modal.UserProfile;
import org.springframework.data.repository.CrudRepository;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer>{

    UserProfile save(UserProfile userProfile);
    UserProfile findUserProfileByUserprofileId(int userProfileId);
}
