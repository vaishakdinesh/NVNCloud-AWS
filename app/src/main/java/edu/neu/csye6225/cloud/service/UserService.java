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
    public User saveUser(String fname, String lname, String useremail, String password) {
        User newUser = new User();
        newUser.setFirstName(fname);
        newUser.setLastName(lname);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(useremail);
        newUser.setRole(Role.USER);
        newUser.setToken(issueUniqueToken());
        newUser.setActive(false);
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
}
