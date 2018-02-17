package edu.neu.csye6225.cloud.modal;

import javax.persistence.*;

@Entity
@Table(name = "userprofile")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userprofileid")
    private int userprofileId;

    @Column(name = "profilepicurl", nullable = false)
    private String profilePicUrl;

    @Column(name = "aboutme", nullable = true, length = 140)
    private String aboutMe;

    public UserProfile() {
    }

    public int getUserprofileId() {
        return userprofileId;
    }

    public void setUserprofileId(int userprofileId) {
        this.userprofileId = userprofileId;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }
}
