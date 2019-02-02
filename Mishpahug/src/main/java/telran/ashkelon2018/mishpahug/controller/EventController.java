package telran.ashkelon2018.mishpahug.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.mishpahug.domain.Event;
import telran.ashkelon2018.mishpahug.dto.NewEventDto;
import telran.ashkelon2018.mishpahug.service.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	
	@Autowired
	EventService eventService;
	
	@PostMapping("/creation")
	public Event createEvent(@RequestHeader("Authorization") String token , @RequestBody NewEventDto newEvent) {
		return eventService.addEvent(newEvent, token);
	}
	
	
	
	
	
	
}
