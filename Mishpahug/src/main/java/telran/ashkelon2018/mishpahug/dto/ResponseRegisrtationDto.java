package telran.ashkelon2018.mishpahug.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResponseRegisrtationDto{
	String[] confession = {"religious","irreligious"};
	String[] gender = {"male","female"};
	String[] maritalStatus = {"married","single","couple"};
	String[] foodPreferences = {"vegetarian","kosher","non-vegetarian"};
	String[] languages = {"Hebrew","English","Russian"};
    String[] holiday = {"Pesah","Shabbat","Other"};
}
