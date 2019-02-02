package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import lombok.Getter;
import telran.ashkelon2018.mishpahug.domain.Address;

@Getter
public class NewEventDto {
	
	String title;
	String holiday;
	Address address;
//	"address": {
//	      "city": "[String]",
//	      "place_id":"[String]",
//	      "location":   {
//	           "lat": "[Double]",
//	           "lng":"[Double]"
//	      }
//	 },
	 String confession;
	 LocalDate date; //":"[yyyy-MM-dd]",
	 LocalTime time; //":"[HH:mm:ss]",
	 Integer duration;
	 Set<String> food;
	 String description;
	
	
}
