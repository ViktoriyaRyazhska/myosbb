package org.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Chat;
import com.softserve.osbb.repository.ChatRepository;
import com.softserve.osbb.service.ChatService;
import com.softserve.osbb.service.impl.ChatServiceImpl;



public class ChatServiceTest {

	
	@Mock
	private Chat chat;
	
	@Mock
	private ChatService chatService;
	
	@Mock
	private List<Chat> list ;
	
	@Mock
	private ChatRepository chatRep;
	
	@InjectMocks
	private ChatServiceImpl chatServiceImpl = new ChatServiceImpl();


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deleteAllTest() {
	chatServiceImpl.deleteAll();
	verify(chatRep).deleteAll();
   }
	
	@Test
	public void updateTest() {
		chatServiceImpl.update(chat);
		verify(chatRep).save(chat);
	}
	
	@Test
	public void findOneTest() {
		when(chatServiceImpl.findOne(1)).thenReturn(chat);
		when(chatServiceImpl.findOne("2")).thenReturn(chat);
		
		chatServiceImpl.findOne(1);
		chatServiceImpl.findOne("2");
		chatServiceImpl.findOne("q");
		
		verify(chatRep).findOne(1);
	}
	
	@Test
	public void  saveTest() {
		chatServiceImpl.save(chat);
		verify(chatRep).save(chat);
	}
	
	@Test
	public void  delete() {
	chatServiceImpl.delete(chat);
	chatServiceImpl.delete(1);
	}
}
