package edu.neu.csye6225.cloud.controller;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.service.EmailServiceImp;
import edu.neu.csye6225.cloud.service.IUserService;
import edu.neu.csye6225.cloud.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private EmailServiceImp emailServiceImp;

    /**
     * Check for duplicate user
     * @return plain text message
     */
    @ResponseBody
    @RequestMapping(value = "/email-check", method = RequestMethod.POST, produces = "text/plain")
    public String isDuplicateUser(@RequestParam String userEmail){
        try{
            User user = iUserService.findUserByEmail(userEmail);
            if(user != null){
                return String.format("%s already in use. Please Log-In", user.getEmail());
            } else {
                return "";
            }

        } catch (Exception ex){
            return "Unable to validate your email now. Try again later.";
        }
    }

    /**
     * Register new user
     * @param user
     * @return success or failure message
     */
    @ResponseBody
    @RequestMapping(value = "/register-user", method = RequestMethod.POST, produces = "text/plain")
    public String registerUser(@RequestBody User user, HttpServletRequest request){
        try {
           if(user.getEmail().isEmpty()){
               return "user Email is a required field.";
           }
           if(user.getFirstName().isEmpty()){
               return "user First Name is a required field.";
           }
           if(user.getLastName().isEmpty()){
                return "user Last Name is a required field.";
           }
           if(user.getPassword().isEmpty()){
               return "Password is a required field.";
           }
           if(Utility.isEmailValid(user.getEmail())){
               User savedUser = iUserService.saveUser(user);
               if(savedUser != null){
                   emailServiceImp.sendVerificationMail(savedUser,request.getRequestURL().toString());
                   return "User registration successful. Please activate your account by using activation link sent to your email.";
               } else {
                   return "Unable to register now. Try again later.";
               }
           } else{
               return "Invalid Email format.";
           }

        } catch (Exception ex){
            return "Unable to register now. Try again later.";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/registration-confirmation", method = RequestMethod.GET, produces = "text/plain")
    public String activateUser(@RequestParam String token){
        if(!token.isEmpty()){
            if(iUserService.activateUser(token)){
                return "Your account is active now.";
            }
        }
        return "Unable to activate your account now. Please email us at csyenvncloud@gmail.com";
    }

}
