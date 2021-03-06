package edu.neu.csye6225.cloud.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.neu.csye6225.cloud.modal.User;
import edu.neu.csye6225.cloud.modal.UserProfile;
import edu.neu.csye6225.cloud.service.IAmazonS3Client;
import edu.neu.csye6225.cloud.service.IUserProfileService;
import edu.neu.csye6225.cloud.service.IUserService;
import edu.neu.csye6225.cloud.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserProfileController {
	
	@Autowired
	private User user;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserProfileService userProfileService;
	
	@ResponseBody
	@RequestMapping(value="/getUser", method=RequestMethod.GET)
	public User getLoggedInUser(Principal principal){
		user = null;
		String email = principal.getName();
		user = userService.findUserByEmail(email);
		return user;
	}
	
	@RequestMapping(value="/welcome/profile")
	public String goProfile(){
		return "redirect:/welcome";
	}
	
	@ResponseBody
	@RequestMapping(value="/profile/update/{id}", method = RequestMethod.POST)
	public void updateUserProfile(@PathVariable Integer id, @RequestParam String about){
		userProfileService.updateUserProfileAboutMe(id, about);
	}
	
	@ResponseBody
	@RequestMapping(value ="/profile/update/picture/{id}", method = RequestMethod.POST)
	public UserProfile uploadProfilePic(@PathVariable Integer id, @RequestParam MultipartFile file){
		UserProfile userProfile=userProfileService.updateUserProfilePicUrl(id, file);
		return userProfile;
	}

	@RequestMapping(value ="/profile/delete/picture/{id}", method = RequestMethod.GET)
	public String uploadProfilePic(@PathVariable Integer id){
		userProfileService.deleteUserProfilePic(id);
		return "redirect:/welcome";
	}
	
	/**
	 * 
	 * @return returns the view only version of the profile
	 */
	@RequestMapping(value="/viewprofile", method=RequestMethod.GET)
	public String viewOnlyProfile(){
		return "viewonlyprofile.html";
	}
	
	@ResponseBody
	@RequestMapping(value="/getuserprofile", method=RequestMethod.GET)
	public User getUserToView(@RequestParam String email){
		user = null;
		user = userService.findUserByEmail(email);
		return user;
	}
}
