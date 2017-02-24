package com.softserve.osbb.test;

import static org.junit.Assert.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.MessageRepository;
import com.softserve.osbb.service.MessageService;
import com.softserve.osbb.service.impl.MessageServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class MessageServiceTest {

	@InjectMocks
	private MessageServiceImpl messageServiceImpl;
	
	@Mock
	private MessageService messageService;
	
	@Mock
	private MessageRepository messageRepository;
	
	@Mock
	private Message message;
	
	@Mock
	private Sort sort;
	
	@Mock
	private Ticket ticket;
	
	@Mock
	private PageRequest pageRequest;
	
	@Mock
	private List<Message> messages;
	
	@Mock
	private Page<Message> messagePage;
	
	@Mock
	private Iterable<Integer> iterable;
	
	@Mock
	private Iterable<Message> Iterable;
	
	@Mock
	private Iterable<? extends Message> iter;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveTest() {		
		Message message = new Message();
		Ticket ticket = new Ticket();
		User user = new User();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		
		message.setMessageId(1);
		message.setParentId(1);
		message.setTicket(ticket);
		message.setTime(timestamp);
		message.setUser(user);
		
		when(messageServiceImpl.save(message)).thenReturn(message);
		when(messageServiceImpl.save(Iterable)).thenReturn(messages);
		
		messageServiceImpl.save(message);
		messageServiceImpl.save(Iterable);
		
		assertNotNull(message);
		assertEquals(user, message.getUser());
		
		verify(messageRepository, times(1)).save(message);
		verify(messageRepository, times(1)).save(Iterable);
	}
	
	@Test 
	public void findOneTest() {
		when(messageServiceImpl.findOne(1)).thenReturn(message);
		when(messageServiceImpl.findOne(2)).thenReturn(message);
		when(messageServiceImpl.findOne("3")).thenReturn(message);
		
		messageServiceImpl.findOne(1);
		messageServiceImpl.findOne(2);
		messageServiceImpl.findOne("3");
		messageServiceImpl.findOne("d");
		
		verify(messageRepository, times(1)).findOne(1);
		verify(messageRepository, times(1)).findOne(2);
		verify(messageRepository, times(1)).findOne(Integer.parseInt("3"));
	}
	
	@Test
	public void existsTest() {
		when(messageServiceImpl.exists(1)).thenReturn(true);
		when(messageServiceImpl.exists(2)).thenReturn(true);
		
		messageServiceImpl.exists(1);
		messageServiceImpl.exists(2);
		
		verify(messageRepository, times(1)).exists(1);
		verify(messageRepository, times(1)).exists(2);
	}
	
	@Test
	public void findAllTest() {
		when(messageServiceImpl.findAll()).thenReturn(messages);
		when(messageServiceImpl.findAll(sort)).thenReturn(messages);
		when(messageServiceImpl.findAll(iterable)).thenReturn(messages);
		
		messageServiceImpl.findAll();
		messageServiceImpl.findAll(sort);
		messageServiceImpl.findAll(iterable);
		
		verify(messageRepository, times(1)).findAll();
		verify(messageRepository, times(1)).findAll(sort);
		verify(messageRepository, times(1)).findAll(iterable);
	}
	
	@Test
	public void countTest() {		
		messageServiceImpl.count();
		
		verify(messageRepository, times(1)).count();
	}
	
	@Test
	public void deleteTest() {
		Message message = new Message();
		Message message2 = new Message();
		
		message.setMessageId(1);
		
		message2.setMessageId(2);
		
		messageServiceImpl.save(message);
		
		doNothing().when(messageService).delete(1);
		doNothing().when(messageService).delete(2);
		doNothing().when(messageService).delete(message);
		doNothing().when(messageService).delete(iter);
		when(messageServiceImpl.exists(message.getMessageId())).thenReturn(true);
		when(messageServiceImpl.exists(message2.getMessageId())).thenReturn(false);
		
		messageServiceImpl.delete(1);
		messageServiceImpl.delete(2);
		messageServiceImpl.delete(message);
		messageServiceImpl.delete(iter);
		
		verify(messageRepository, times(2)).delete(1);
		verify(messageRepository, times(0)).delete(2);
		verify(messageRepository, times(1)).delete(iter);
	}
	
	@Test
	public void deleteAllTest() {
		doNothing().when(messageService).deleteAll();
		
		messageServiceImpl.deleteAll();
		
		verify(messageRepository, times(1)).deleteAll();
	}
	
	@Test
	public void flushTest() {
		doNothing().when(messageService).flush();
		
		messageServiceImpl.flush();
		
		verify(messageRepository, times(1)).flush();
	}
	
	@Test
	public void getOneTest() {
		when(messageServiceImpl.getOne(1)).thenReturn(message);
		when(messageServiceImpl.getOne(2)).thenReturn(message);
		
		messageServiceImpl.getOne(1);
		messageServiceImpl.getOne(2);
		
		verify(messageRepository, times(1)).getOne(1);
		verify(messageRepository, times(1)).getOne(2);
	}
	
	@Test
	public void saveAndFlushTest() {
		when(messageServiceImpl.saveAndFlush(message)).thenReturn(message);
		
		messageServiceImpl.saveAndFlush(message);
		
		verify(messageRepository, times(1)).saveAndFlush(message);
	}
	
	@Test
	public void updateTest() {
		Message message = new Message();
		Message message2 = new Message();
		
		message.setMessageId(1);
		
		message2.setMessageId(2);
		
		messageServiceImpl.save(message);
		
		when(messageService.update(message)).thenReturn(message);
		when(messageService.update(message)).thenReturn(null);
		when(messageRepository.exists(message.getMessageId())).thenReturn(true);
		when(messageRepository.exists(message2.getMessageId())).thenReturn(false);
		
		messageServiceImpl.update(message);
		messageServiceImpl.update(message2);
		
		verify(messageRepository, times(1)).exists(message.getMessageId());
		verify(messageRepository, times(1)).exists(message2.getMessageId());
		verify(messageRepository, times(2)).save(message);
		verify(messageRepository, times(0)).save(message2);
	}
	
	@Test
	public void findMessagesByTicketTest() {
		when(messageServiceImpl.findMessagesByTicket(ticket, pageRequest)).thenReturn(messagePage);
		
		messageServiceImpl.findMessagesByTicket(ticket, pageRequest);
		
		verify(messageRepository, times(1)).findByTicketOrderByTimeDesc(ticket, pageRequest);
	}
	
	@Test
	public void getAnswersTest() {
		when(messageServiceImpl.getAnswers(1)).thenReturn(messages);
		when(messageServiceImpl.getAnswers(2)).thenReturn(messages);
		
		messageServiceImpl.getAnswers(1);
		messageServiceImpl.getAnswers(2);
		
		verify(messageRepository, times(1)).findByParentIdOrderByTimeDesc(1);
		verify(messageRepository, times(1)).findByParentIdOrderByTimeDesc(2);
	}

}
