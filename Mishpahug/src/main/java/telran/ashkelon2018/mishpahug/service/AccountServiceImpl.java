package telran.ashkelon2018.mishpahug.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import telran.ashkelon2018.mishpahug.api.Confession;
import telran.ashkelon2018.mishpahug.api.Directory;
import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.ResponseRegisrtationDto;
import telran.ashkelon2018.mishpahug.dto.UserProfileDto;
import telran.ashkelon2018.mishpahug.exceptions.UserConflictException;
import telran.ashkelon2018.mishpahug.exceptions.UserUnauthorizedException;
import telran.ashkelon2018.mishpahug.exceptions.UserUnprocessableEntity;

@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	UserAccountRepository userRepository;

	@Autowired
	AccountConfiguration accountConfiguration;

	public static final Pattern VALIDATE_EMAIL = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static final Pattern VALIDATE_PASSWORD = 
			Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
					Pattern.CASE_INSENSITIVE);
	
	@Override
	public ResponseRegisrtationDto addUser(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (validateEmail(credentials.getEmail()) == false || validatePassword(credentials.getPassword()) == false) {
			throw new UserUnprocessableEntity();
		}
		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserConflictException("User exists!");
		}
		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
		UserAccount userAccount = UserAccount
				.builder()
				.email(credentials.getEmail())
				.password(hashPassword)
				.role("ROLE_USER")
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();
		userRepository.save(userAccount);
		return new ResponseRegisrtationDto();
	}
			
	
	@Override
	public UserProfileDto editUser(UserProfileDto userProfileDto, String token) {
		UserAccount userAccount = checkUserInRepo(token);
		UserProfileDto userProfile = null;
		for (Field field : userAccount.getClass().getFields()) {
			if (field.getName().equals("email") && !field.equals(null)) {
				userProfile = convertToUserProfileDto(
						userRepository.save(
								convertProfileToUserAccount(userAccount,userProfileDto)));
			}
		}
		return userProfile;
	}

	@Override
	public UserProfileDto login(String token) {
		UserAccount tmpUser = checkUserInRepo(token);
		for (Field field : tmpUser.getClass().getFields()) {
			if (field.getName().equals("email") && !field.equals(null)) {
				return convertToUserProfileDto(tmpUser);
			}
		}
		throw new UserConflictException();
	}
	
	@Override
	public UserProfileDto removeUser(String login, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> addRole(String login, String role, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> removeRole(String login, String role, String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePassword(String password, String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserProfileDto getUser(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	
//******************************************************************************
//System methods
//******************************************************************************	
	private UserProfileDto convertToUserProfileDto(UserAccount userAccount) {
		return UserProfileDto
				.builder()
//				.email(userAccount.getEmail())
				.firstName(userAccount.getFirstName())
				.dateOfBirth(userAccount.getDateOfBirth())
				.gender(userAccount.getGender())
				.maritalStatus(userAccount.getMaritalStatus())
				.confession(userAccount.getConfession())
				.lastName(userAccount.getLastName())
				.pictureLink(userAccount.getPictureLink())
				.phoneNumber(userAccount.getPhoneNumber())
				.foodPreferences(userAccount.getFoodPreferences())
				.languages(userAccount.getLanguages())
				.description(userAccount.getDescription())
				.rate(0.0)
				.numberOfVoters(0)
				.build();
	}
	
	public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALIDATE_EMAIL.matcher(emailStr);
        return matcher.find();
	}
	public static boolean validatePassword(String passw) {
		Matcher matcher = VALIDATE_PASSWORD.matcher(passw);
		return matcher.find();
		/*
		 	^                 # start-of-string
			(?=.*[0-9])       # a digit must occur at least once
			(?=.*[a-z])       # a lower case letter must occur at least once
			(?=.*[A-Z])       # an upper case letter must occur at least once
			(?=.*[@#$%^&+=])  # a special character must occur at least once
			(?=\S+$)          # no whitespace allowed in the entire string
			.{8,}             # anything, at least eight places though
			$                 # end-of-string
		 */
	}
	
	private UserAccount checkUserInRepo(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).orElseThrow(
				() -> new UserUnauthorizedException());
		// FIXME need to change with real security method with sult checking
		if (!userAccount.getPassword().equals(credentials.getPassword())) {
			throw new UserUnauthorizedException();
		}
		return userAccount;
	}
	
	private UserAccount convertProfileToUserAccount(UserAccount userAccount, UserProfileDto userProfileDto) {
		userAccount.setFirstName(userProfileDto.getFirstName());
		userAccount.setLastName(userProfileDto.getLastName());
		userAccount.setConfession(userProfileDto.getConfession());
		userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
		userAccount.setDescription(userProfileDto.getDescription());
		userAccount.setEmail(userAccount.getEmail());//we can use the same email because we already check this early
		userAccount.setExpdate(userAccount.getExpdate());
		userAccount.setFoodPreferences(userProfileDto.getFoodPreferences());
		userAccount.setGender(userProfileDto.getGender());
		userAccount.setLanguages(userProfileDto.getLanguages());
		userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
		userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
		userAccount.setNumberOfVoters(userProfileDto.getNumberOfVoters());
		userAccount.setPictureLink(userProfileDto.getPictureLink());
		userAccount.setRate(userProfileDto.getRate());
		userAccount.setRoles(userAccount.getRoles());
		return userAccount;
	}
}
