package telran.ashkelon2018.mishpahug.dto;

import java.time.LocalDate;

public class NotificationDto {

	 Long notificationId;
     String title;
     String message;
     LocalDate date;
     String type; //": "System",
     boolean isRead;
     Long eventId;
}
