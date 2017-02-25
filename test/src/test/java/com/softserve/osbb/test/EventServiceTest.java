package com.softserve.osbb.test;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;

import com.softserve.osbb.model.Event;
import com.softserve.osbb.repository.EventRepository;
import com.softserve.osbb.service.EventService;
import com.softserve.osbb.service.impl.EventServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class EventServiceTest {
	
	@InjectMocks
	private EventServiceImpl eventServiceImpl;
	
	@Mock
	private EventService eventService;
	
	@Mock
	private EventRepository eventRepository;
	
	@Mock
	private Event event;
	
	@Mock
	private List<Event> events;
	
	@Mock
	private Page<Event> page;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveEventTest() {
		when(eventServiceImpl.saveEvent(event)).thenReturn(event);
		
		eventServiceImpl.saveEvent(event);
		
		verify(eventRepository, times(1)).save(event);
	}
	
	@Test
	public void saveEventsTest() {
		when(eventServiceImpl.saveEvents(events)).thenReturn(events);
		
		eventServiceImpl.saveEvents(events);
		
		verify(eventRepository, times(1)).save(events);
	}
	
	@Test
	public void getEventsTest() {
		when(eventServiceImpl.getEvents(events)).thenReturn(events);
		
		eventServiceImpl.getEvents(events);
		
		verify(eventRepository, times(1)).save(events);
	}
	
	@Test
	public void getEventByIdTest() {
		when(eventServiceImpl.getEventById(1)).thenReturn(event);
		when(eventServiceImpl.getEventById(2)).thenReturn(event);
		
		eventServiceImpl.getEventById(1);
		eventServiceImpl.getEventById(2);
		
		verify(eventRepository, times(1)).findOne(1);
		verify(eventRepository, times(1)).findOne(2);
	}
	
	@Test
	public void getEventsByIdsTest() {
		when(eventServiceImpl.getEventsByIds(null)).thenReturn(events);
		
		eventServiceImpl.getEventsByIds(null);
		
		verify(eventRepository, times(0)).findAll();
	}
	
	@Test
	public void getAllEventsTest() {
		when(eventServiceImpl.getAllEvents()).thenReturn(events);
		when(eventServiceImpl.getAllEvents(1, "number", true)).thenReturn(page);
		when(eventServiceImpl.getAllEvents(1, "number", null)).thenReturn(page);
		when(eventServiceImpl.getAllEvents(1, null, null)).thenReturn(page);
		
		eventServiceImpl.getAllEvents();
		eventServiceImpl.getAllEvents(1, "number", true);
		eventServiceImpl.getAllEvents(1, "number", null);
		eventServiceImpl.getAllEvents(1, null, null);
		
		verify(eventRepository, times(1)).findAll();
	}
	
	@Test
	public void updateEventTest() {
		Event event = new Event();
		Event event2 = new Event();
		
		event.setEventId(1);
		event.setAttachments(null);
		event.setAuthor(null);
		event.setDescription("description");
		event.setOsbb(null);
		event.setEndTime(null);
		event.setRepeat(null);
		event.setStartTime(null);
		event.setTitle("title");
		
		event2.setEventId(2);
		
		eventService.saveEvent(event);
		
		when(eventRepository.exists(1)).thenReturn(true);
		when(eventRepository.exists(2)).thenReturn(false);
		when(eventServiceImpl.updateEvent(1, event)).thenReturn(event);
		
		eventServiceImpl.updateEvent(1, event);
		eventServiceImpl.updateEvent(2, event2);
		
		verify(eventRepository, times(2)).exists(1);
		verify(eventRepository, times(1)).exists(2);
		verify(eventRepository, times(1)).save(event);
	}
	
	@Test
	public void deleteEventTest() {
		doNothing().when(eventService).deleteEvent(event);
		
		eventServiceImpl.deleteEvent(event);
		
		verify(eventRepository, times(1)).delete(event);
	}
	
	@Test
	public void deleteEventById() {
		doNothing().when(eventService).deleteEventById(1);
		doNothing().when(eventService).deleteEventById(2);
		
		eventServiceImpl.deleteEventById(1);
		eventServiceImpl.deleteEventById(2);
		
		verify(eventRepository, times(1)).delete(1);
		verify(eventRepository, times(1)).delete(2);
	}
	
	@Test
	public void deleteEventsTest() {
		doNothing().when(eventService).deleteEvents(events);
		
		eventServiceImpl.deleteEvents(events);
		
		verify(eventRepository, times(1)).delete(events);
	}
	
	@Test
	public void deleteAllEventsTest() {
		doNothing().when(eventService).deleteAllEvents();
		
		eventServiceImpl.deleteAllEvents();
		
		verify(eventRepository, times(1)).deleteAll();
	}
	
	@Test
	public void countEventsTest() {
		eventServiceImpl.countEvents();
		
		verify(eventRepository, times(1)).count();
	}
	
	@Test
	public void existsEventTest() {
		when(eventServiceImpl.existsEvent(1)).thenReturn(true);
		when(eventServiceImpl.existsEvent(100)).thenReturn(false);
		
		eventServiceImpl.existsEvent(1);
		eventServiceImpl.existsEvent(100);
		
		verify(eventRepository, times(1)).exists(1);
		verify(eventRepository, times(1)).exists(100);
	}
	
	@Test
	public void findByAuthorTest() {
		when(eventServiceImpl.findByAuthor(anyString())).thenReturn(events);
		
		eventServiceImpl.findByAuthor(anyString());
		
		verify(eventRepository, times(1)).findByAuthorEmail(anyString());
	}
	
	@Test
	public void findEventsByTitleOrAuthorOrDescriptionTest() {
		when(eventServiceImpl.findEventsByTitleOrAuthorOrDescription(anyString())).thenReturn(events);
		
		eventServiceImpl.findEventsByTitleOrAuthorOrDescription(anyString());
		
		verify(eventRepository, times(1)).findByTitleOrAuthorOrDescription(anyString());
	}
	
	@Test
	public void findByIntervalTest() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		when(eventServiceImpl.findByInterval(timestamp, timestamp)).thenReturn(events);
		
		eventServiceImpl.findByInterval(timestamp, timestamp);
		
		verify(eventRepository, times(1)).findBetweenStartTimeAndEndTime(timestamp, timestamp);
	}

}
