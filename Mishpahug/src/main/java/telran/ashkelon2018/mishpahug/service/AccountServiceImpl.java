package telran.ashkelon2018.mishpahug.service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.mishpahug.configuration.AccountConfiguration;
import telran.ashkelon2018.mishpahug.configuration.AccountUserCredentials;
import telran.ashkelon2018.mishpahug.dao.UserAccountRepository;
import telran.ashkelon2018.mishpahug.domain.UserAccount;
import telran.ashkelon2018.mishpahug.dto.ResponseRegisrtationDto;
import telran.ashkelon2018.mishpahug.dto.Roles;
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
			Pattern.compile("\\A(?=\\S*[0-9])(?=\\S*[a-z])(?=\\S*[A-Z])(?=\\S*[@#$%^&+=])\\S{8,}\\z",
					Pattern.CASE_INSENSITIVE);
	
	@Override
	public ResponseRegisrtationDto registrationUser (String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		if (validateEmail(credentials.getEmail()) == false) //|| validatePassword(credentials.getPassword()) == false) {
			throw new UserUnprocessableEntity();
//		}
		if (userRepository.existsById(credentials.getEmail())) {
			throw new UserConflictException("User exists!");
		}
		String hashPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());
		String[] pictureAndBanner = new String[2];
		pictureAndBanner[0] = "";
		pictureAndBanner[1] = "";
		UserAccount userAccount = UserAccount
				.builder()
				.email(credentials.getEmail())
				.password(hashPassword)
				.role("ROLE_USER")
				.pictureLink(pictureAndBanner)
				.expdate(LocalDateTime.now().plusDays(accountConfiguration.getExpPeriod())).build();
		userRepository.save(userAccount);
		return new ResponseRegisrtationDto();
	}
			
	
	@Override
	public UserProfileDto editUser(UserProfileDto userProfileDto, String token) {
		UserAccount userAccount = checkUserInRepo(token);
//		convertProfileToUserAccount(userAccount, userProfileDto);
			if(userProfileDto.getFirstName() != null) {
				userAccount.setFirstName(userProfileDto.getFirstName());
			}else{
				throw new UserUnprocessableEntity();
			}
			if(userProfileDto.getDateOfBirth() != null) {
			userAccount.setDateOfBirth(userProfileDto.getDateOfBirth());
			}
			if(userProfileDto.getGender() != null) {
			userAccount.setGender(userProfileDto.getGender());
			}
			if(userProfileDto.getMaritalStatus() != null) {
			userAccount.setMaritalStatus(userProfileDto.getMaritalStatus());
			}
			if(userProfileDto.getConfession() != null) {
			userAccount.setConfession(userProfileDto.getConfession());
			}
			if(userProfileDto.getLastName() != null) {
			userAccount.setLastName(userProfileDto.getLastName());
			}
			if(userProfileDto.getPictureLink() != null) {
			userAccount.setPictureLink(userProfileDto.getPictureLink());
			}
			if(userProfileDto.getPhoneNumber() != null) {
			userAccount.setPhoneNumber(userProfileDto.getPhoneNumber());
			}
			if(userProfileDto.getFoodPreferences() != null) {
			userAccount.setFoodPreferences(userProfileDto.getFoodPreferences());
			}
			if(userProfileDto.getLanguages() != null) {
			userAccount.setLanguages(userProfileDto.getLanguages());
			}
			if(userProfileDto.getDescription() != null) {
			userAccount.setDescription(userProfileDto.getDescription());
			}
			userRepository.save(userAccount);
		return convertToUserProfileDto(userAccount);
	}

	@Override
	public UserProfileDto loginUser (String token) {
		UserAccount tmpUser = checkUserInRepo(token);
		if (tmpUser!=null) {
			return convertToUserProfileDto(tmpUser);	
		}		
		throw new UserConflictException();
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
	public UserProfileDto getUserProfile(String token) {
		UserAccount userAccount = checkUserInRepo(token);
		if (userAccount!=null) {
			return convertToUserProfileDto(userAccount);	
		}		
		throw new UserConflictException();
		
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
	
	public UserAccount checkUserInRepo(String token) {
		AccountUserCredentials credentials = accountConfiguration.tokenDecode(token);
		UserAccount userAccount = userRepository.findById(credentials.getEmail()).orElseThrow(
				()-> new UserUnauthorizedException());
		if(userAccount.getRoles().contains(Roles.USER_EMPTY_PROFILE.name())) {
			throw new UserConflictException("User has empty profile!");
		}
		// FIXME need to change with real security method with sult checking
//		if (!userAccount.getPassword().equals(credentials.getPassword())) {
//			throw new UserUnauthorizedException();
//		}
		return userAccount;
	}
	
	public UserAccount convertProfileToUserAccount(UserAccount userAccount, UserProfileDto userProfileDto) {
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
	
	public boolean checkFieldsOnNull(UserAccount tmpUser) {
		Field[] fields = tmpUser.getClass().getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				String res = (String)field.get(tmpUser).toString();
				if (res.equals("") || res == null) {
					throw new UserUnprocessableEntity();
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
