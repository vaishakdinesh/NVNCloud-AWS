package edu.neu.csye6225.cloud.controller;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.service.IUserService;
import edu.neu.csye6225.cloud.util.Utility;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    private IUserService iUserService;

    /**
     * Check for duplicate user
     * @return plain text message
     */
    @ResponseBody
    @RequestMapping(value = "/email-check", method = RequestMethod.POST, produces = "text/plain")
    public String isDuplicateUser(@RequestParam String userEmail){
        try{
            User user = iUserService.findUserByEmail(userEmail);
            if(!Utility.isEmptyOrNull(user)){
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
    public String registerUser(@RequestBody User user){
        try {
           //if()

        } catch (Exception ex){
            return "Unable to register now. Try again later.";
        }
        return "";
    }

}
