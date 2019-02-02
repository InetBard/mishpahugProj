package telran.ashkelon2018.mishpahug.service;

import java.util.List;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.EventDone;
import telran.ashkelon2018.mishpahug.dto.EventInProgressOrPendingDto;
import telran.ashkelon2018.mishpahug.dto.FullEventInPOrPDto;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;
import telran.ashkelon2018.mishpahug.dto.NotificationDto;

public interface MishpahugService {
//	UnAuthorized requests
//	+++++++++++++++++++++++++++++++++++++++++
//	GET
//	Static fields for profile form
	String getStaticFieldsForProfileForm();
	
//	POST
//	List of Events in progress
	Iterable<Event> findEventsInProgress(List<String> tags, int page, int size);
	
//	Authorized requests
//	+++++++++++++++++++++++++++++++++++++++++
//	=========================
//	POST 
//	Registration User
//	POST 
//	Login User
//	=========================
	
//	POST 
//	Add Event
//	1) User can create an event at least two days 
//before the date of this event. For example, 
//if the event will take place at 10.05.2018, 
//user should create this event at least 
//until 08.05.2018 00.00;
//
//	2) Owner of the event should change the status of 
//event to "Pending" at least one day before the date 
//of this event. If user have not done it the event will be 
//authomatically changed to status "Not done" 
//and all participants will be notified about 
//cancellation of the event. For example, 
//if the event will take place at 10.05.2018, 
//user should change status of this event at least 
//until 09.05.2018 01.00;
//
//	3) If User have changed status to "Pending" timely 
//and event haven't been cancelled for other reasons, 
//the event will be authomatically changed to status "Done" 
//after its ending and all participants will be notified 
//to vote this event.
	
	Event addEvent(NewEventDto newEventDto, String token);
	
//	==============================
//	POST
//	Update User Profile
//	GET
//	User Profile
//	====================================
	
//	GET
//	Events List for Calendar
//	User receives list of events at status "in progress" and "pending" which this user created or subscribed due to specified month.
//	Path variable {month} : number of month (1 – 12) - Integer
//	Пользователь получает список событий со статусом 
//	«в процессе» и «в ожидании», которые этот пользователь 
//	создал или подписал в связи с указанным месяцем.
//	Переменная пути {месяц}: номер месяца (1 - 12) - целое число
	Iterable<Event> findEventsForCalendar(Integer month, String token);
	
//	GET
//	My Event Info
//	User receives the event that he created.
//	Пользователь получает событие, которое он создал.

	FullEventInPOrPDto getEvent(Long eventId, String token);
	
//	GET
//	Subscribed Event Info
//	User receives the event to he is subscribed.
//	Пользователь получает событие, на которое он подписан.

	EventInProgressOrPendingDto findEventsSubscribed(Long eventId, String token); 
	
//	GET
//	Notifications List
//	Client receives list of his notifications. 
//	The list should be sorted by date in descending order
//	and unread notifications at the top of the list.
//	Клиент получает список своих уведомлений.
//	 Список должен быть отсортирован по дате 
//	в порядке убывания 
//	 и непрочитанных уведомлений вверху списка.
	 
	Iterable<NotificationDto> findNotifications(String token);
	
//	GET
//	My Events List
//	User receives list of events at status 
//	"in progress" and "pending" which this user created. 
//	The list should be sorted by date in ascending order.
//	Пользователь получает список событий в статусе 
//	«в процессе» и «в ожидании», которые создал этот пользователь. 
//	Список должен быть отсортирован по дате в порядке возрастания
	
	Iterable<FullEventInPOrPDto> findMyEventList(String token);
	
//	GET
//	My Events History
//	User receives list of all events at status 
//	"done" that he created. 
//	The list should be sorted by date in descending order.
//	Пользователь получает список всех событий 
//	в статусе «выполнено», которые он создал. 
//	Список должен быть отсортирован по дате в порядке убывания.
	
	Iterable<EventDone> fintMyEventsHistory(String token);
	
//	GET
//	Participation List
//	User receives list of events which user subscribed 
//	or will participate. Events at status "done" in which 
//	user participated includes into list only 
//	if user didn’t vote them. 
//	The list should be sorted by date in ascending order.
//	Пользователь получает список событий, на которые 
//	пользователь подписался или будет участвовать. 
//	События со статусом «выполнено», в которых участвовал 
//	пользователь, включаются в список, только 
//	если пользователь не голосовал за них. 
//	Список должен быть отсортирован по дате в порядке возрастания.
	
	Iterable<Event> findParticipationList();
//	FIXED

//	PUT
//	Subscribe to event
//	User can subscribe to multiple events 
//	on the same date with the only condition: 
//	if he will be confirmed/invited 
//	to one the subscribed events on this date, 
//	the other subscriptions will be cancelled.
//	Пользователь может подписаться на несколько событий
//	в одну и ту же дату с единственным условием: 
//	если он будет подтвержден / приглашен на одно из 
//	подписанных событий в эту дату, 
//	остальные подписки будут отменены.
	
	boolean subscribeToEvent(Long eventId, String token);
		
//	PUT
//	Unsubscribe from event
//	User can unsubscribe from the event only 
//	if the event is at status "In progress".
//	Пользователь может отказаться от подписки на событие, 
//	только если событие находится в статусе «В процессе».
	
	boolean unsubscribeFromEvent(Long eventId, String token);
	
//	PUT
//	Vote for event
//	Проголосуй за событие
//	Participant votes for the event.
//	Path variables:
//	{eventId} - ID number of event - Long;
//	{voteCount} - vote of event - Double;
//	Участник голосует за мероприятие.
//	Переменные пути:
//	{eventId} - идентификационный номер события - Long;
//	{voteCount} - голосование события - Double;
	
	boolean voteForEvent(Long eventId, Double voteCount, String token);
	
//	PUT
//	Invite to event
//	Event owner confirmed the subscriber to participate in the event.
//	Path variable {userId} : ID number of event participant - Long
//	Владелец мероприятия подтвердил подписчику участие в мероприятии.
//	Переменная пути {userId}: идентификационный номер участника события - Long

	boolean inviteToEvent(Long eventId, Long userId, String token);
	
//	PUT
//	Change event status
//	Owner changes event status on "Pending".
	
	boolean changeEventStatus (Long eventId, String token);
	
//	PUT
//	Notification is read
//	User notifies the server that he has read the notification.
//	Пользователь уведомляет сервер о том, что он прочитал уведомление.
	
	boolean notificationIsRead(Long notificationId, String token);
	
//	GET
//	Count unread notifications
//	User receives number of unread notifications.
	
	Integer countUnreadNotifications(String token);
	
//	POST
//	Add firebase token
//	When user logs in, the client side should register 
//	him on Firebase server and receive special token from it. 
//	This token should be sent to server side to enable 
//	notifications. After sending request client side should 
//	save this token until the end of user's session.
	
	String addFirebaseToken(String token);
	
//	DEL
//	Delete firebase token
//	When user logs out, the client side should send 
//	saved token to server side to disable notifications.
	
	String deleteFirebaseToken(String token);
	
}
