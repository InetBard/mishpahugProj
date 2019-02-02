package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.Builder.Default;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = {"email"})
@Document(collection = "user_mishpahug")
public class UserAccount {
		@Id
		String email;
		String password;
		
		String firstName;
		String lastName;
		LocalDate dateOfBirth;
		String gender;
		String maritalStatus;
		String confession;
		String[] pictureLink;
		
		String phoneNumber;//FIXME type of field
		Set<String> foodPreferences;
		Set<String> languages;
		String description;
		@Default
		Double rate = 0.0;//FIXME type of field
		@Default
		Integer numberOfVoters = 0;

		@Singular
		Set<String> roles;
		LocalDateTime expdate; // change password from period
		
		public void addRole(String role) {
			roles.add("ROLE_"+role.toUpperCase());
		}

		public void removeRole(String role) {
			roles.remove("ROLE_"+role.toUpperCase());
			
		}
}
