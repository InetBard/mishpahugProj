package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
//	String email; FIXME we already get this data from Credential
	String firstName;
	String lastName;
	LocalDate dateOfBirth;
	String gender;
	String maritalStatus;
	String confession;
	String[] pictureLink;
	String phoneNumber;
	Set<String> foodPreferences;
	Set<String> languages;
	String description;
	Double rate;
	Integer numberOfVoters;//FIXME need to think should we have use this here in this class
//	Set<String> roles;
}
