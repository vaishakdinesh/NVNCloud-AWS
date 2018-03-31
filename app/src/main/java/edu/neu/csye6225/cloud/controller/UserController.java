package edu.neu.csye6225.cloud.controller;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.service.EmailServiceImp;
import edu.neu.csye6225.cloud.service.IUserService;
import edu.neu.csye6225.cloud.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    public String isDuplicateUser(@RequestParam String email){
        try{
            User user = iUserService.findUserByEmail(email);
            if(user != null){
                return String.format("%s already in use. Please Log-In", user.getEmail());
            } else {
                return "";
            }

        } catch (Exception ex){
            return "Unable to validate your email now. Try again later.";
        }
    }


    @ResponseBody
    @RequestMapping(value = "/register-user", method = RequestMethod.POST, produces = "text/plain")
    public String registerUser(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String useremail, @RequestParam String password, HttpServletRequest request){
        try {
           if(useremail.isEmpty()){
               return "user Email is a required field.";
           }
           if(firstname.isEmpty()){
               return "user First Name is a required field.";
           }
           if(lastname.isEmpty()){
                return "user Last Name is a required field.";
           }
           if(password.isEmpty()){
               return "Password is a required field.";
           }
           if(Utility.isEmailValid(useremail)){
               User savedUser = iUserService.saveUser(firstname, lastname, useremail, password);
               if(savedUser != null){
                   emailServiceImp.sendVerificationMail(savedUser,
                           request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort());
                   return "User registration successful. Please activate your account by using activation link sent to your email.";
               } else {
                   return "Unable to save the user. Try again later.";
               }
           } else{
               return "Invalid Email format.";
           }

        } catch (Exception ex){
            return "Unable to register now. Try again later.";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = "text/plain")
    public String sendResetLink(@RequestParam String useremail){
    	iUserService.notifySNS(useremail);
    	return "An email has been sent with a reset link which will be valid for 20 min.";
    }
    
    @ResponseBody
	@RequestMapping(value="/resetpassword", method=RequestMethod.POST)
	public User resetPassword(@RequestParam String password, HttpServletRequest req){
		String email = (String) req.getSession().getAttribute("resetEmail");
		System.out.println(email);
		User user = iUserService.updatePassword(email, password);
		return user;
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

    @ResponseBody
    @RequestMapping(value = "/registration-confirmation-jmeter", method = RequestMethod.GET, produces = "text/plain")
    public String activateUserJmeter(@RequestParam String email){
        if(!email.isEmpty()){
            if(iUserService.activateUserJmeter(email)){
                return "Your account is active now.";
            }
        }
        return "Unable to activate your account now. Please email us at csyenvncloud@gmail.com";
    }


}
