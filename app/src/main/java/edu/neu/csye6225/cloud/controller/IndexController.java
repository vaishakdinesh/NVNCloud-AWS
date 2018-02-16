package edu.neu.csye6225.cloud.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
