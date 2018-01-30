package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.modal.User;

import java.util.List;

public interface IUserService {

    User findUserByEmail(String email);
    User saveUser(User user);
}
