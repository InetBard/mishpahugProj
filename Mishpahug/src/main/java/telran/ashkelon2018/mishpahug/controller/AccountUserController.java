package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.dto.ResponseRegisrtationDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.service.AccountService;

@RestController
@RequestMapping("/user")
public class AccountUserController {
	
	@Autowired
	AccountService accountService;
	
	@PostMapping("/registration")
	public ResponseRegisrtationDto addUser(@RequestHeader("Authorization") String token) {
		return accountService.addUser(token);
	}
	
	@PostMapping("/login/{login}")
	public UserProfileDto login(@PathVariable String token) {
		return accountService.login(token);
	}

	@PostMapping("/profile")
	UserProfileDto editUser(UserProfileDto userProfileDto, String token) {
		return accountService.editUser(userProfileDto, token);
	}
//
//	UserProfileDto removeUser(String login, String token);
//
//	Set<String> addRole(String login, String role, String token);
//
//	Set<String> removeRole(String login, String role, String token);
//
//	void changePassword(String password, String token);
//
//	UserProfileDto getUser(String login);
	
	
	
	
}
