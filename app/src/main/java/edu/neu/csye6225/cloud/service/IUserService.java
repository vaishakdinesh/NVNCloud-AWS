package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.User;

import javax.security.sasl.SaslServer;
import java.util.List;

public interface IUserService {

    User findUserByEmail(String email);
    User findUserByUserId(int id);
    User saveUser(String fname, String lname, String useremail, String password);
    boolean activateUser(String token);
    boolean activateUserJmeter(String email);
    void notifySNS(String email);
    User updatePassword(String email,String password);
}
