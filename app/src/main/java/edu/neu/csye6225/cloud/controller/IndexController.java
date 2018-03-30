package edu.neu.csye6225.cloud.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

@Controller
public class IndexController {

    /**
     * Redirect to log-in page
     * @return log-in page name
     */
    @RequestMapping(value = "/")
    public String goLogin(){
        return "log-in.html";
    }

    @RequestMapping(value = "/welcome")
    public String goWelcome(){
    	return "home.html";
    }

    @RequestMapping("/hw")
    public @ResponseBody String greeting() {
        return "Hello World";
    }
    
    @RequestMapping(value="/resetpassword", method=RequestMethod.GET)
    public String goForgot() {
        return "forgot.html";
    }
    
    @RequestMapping(value = "/updatepassword/**", method=RequestMethod.GET)
    public String goReset(@RequestParam("email") String email,HttpServletRequest req){
    	req.getSession().setAttribute("resetEmail", email);
    	return "passwordreset.html";
    }
}
