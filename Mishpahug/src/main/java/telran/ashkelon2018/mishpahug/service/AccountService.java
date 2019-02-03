package telran.ashkelon2018.mishpahug.service;

import java.util.Set;

import telran.ashkelon2018.mishpahug.dto.ResponseRegisrtationDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;


public interface AccountService {
	
//	Authorized requests
//	POST 
//	Registration User
	
	/*UserProfileDto*/ResponseRegisrtationDto registrationUser(String token);
	
//	POST
//	Update User Profile
	
	UserProfileDto editUser(UserProfileDto userProfileDto, String token);

	Set<String> addRole(String login, String role, String token);

	Set<String> removeRole(String login, String role, String token);

	void changePassword(String password, String token);
//	GET
//	User Profile
	UserProfileDto getUserProfile(String login);
//	POST 
//	Login User
	UserProfileDto loginUser(String token);
}
