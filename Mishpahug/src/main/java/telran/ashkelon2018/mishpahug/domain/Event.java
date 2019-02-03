package telran.ashkelon2018.mishpahug.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_mishpahug")
public class Event {
	Long eventId;
	String title;
	List<String> holiday;
	Address address;
	String confession;
	LocalDate dateFrom;
	LocalDate dateTo;
	LocalDateTime time;
	int duration;
	List<String> food;
	String description;
}
