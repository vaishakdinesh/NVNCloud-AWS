package edu.neu.csye6225.cloud.service;

import edu.neu.csye6225.cloud.enums.Role;
import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        return user;
    }

    @Override
    public User saveUser(User user) {
        user.setRole(Role.USER);
        user.setActive(false);
        user.setToken(issueUniqueToken());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return savedUser;
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

    private String issueUniqueToken(){
        String token = "";
        do {
            token = getUniqueToken();
        } while (userRepository.findUserByToken(token) == null);
        return token;
    }

    private String getUniqueToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }
}
