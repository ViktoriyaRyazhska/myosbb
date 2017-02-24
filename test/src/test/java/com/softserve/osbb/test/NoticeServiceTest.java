package com.softserve.osbb.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Notice;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.NoticeRepository;
import com.softserve.osbb.service.NoticeService;
import com.softserve.osbb.service.impl.NoticeServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class NoticeServiceTest {
	
	@InjectMocks
	private NoticeServiceImpl noticeServiceImpl;
	
	@Mock
	private NoticeService noticeService;
	
	@Mock
	private NoticeRepository noticeRepository;
	
	@Mock
	private Notice notice;
	
	@Mock
	private User user;
	
	@Mock
	private List<Notice> notices;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveTest() {
		when(noticeServiceImpl.save(notice)).thenReturn(notice);
		
		noticeServiceImpl.save(notice);
		
		verify(noticeRepository, times(1)).save(notice);
	}
	
	@Test
	public void findOneTest() {
		when(noticeServiceImpl.findOne(1)).thenReturn(notice);
		when(noticeServiceImpl.findOne(2)).thenReturn(notice);
		when(noticeServiceImpl.findOne("3")).thenReturn(notice);
		
		noticeServiceImpl.findOne(1);
		noticeServiceImpl.findOne(2);
		noticeServiceImpl.findOne("3");
		noticeServiceImpl.findOne("e");
		
		verify(noticeRepository, times(1)).findOne(1);
		verify(noticeRepository, times(1)).findOne(2);
		verify(noticeRepository, times(1)).findOne(Integer.parseInt("3"));
	}
	
	@Test
	public void existsTest() {
		when(noticeServiceImpl.exists(1)).thenReturn(true);
		when(noticeServiceImpl.exists(2)).thenReturn(true);
		
		noticeServiceImpl.exists(1);
		noticeServiceImpl.exists(2);
		
		verify(noticeRepository, times(1)).exists(1);
		verify(noticeRepository, times(1)).exists(2);
	}
	
	@Test
	public void deleteTest() {
		when(noticeServiceImpl.exists(1)).thenReturn(true);
		doNothing().when(noticeService).delete(1);
		doNothing().when(noticeService).delete(2);
		
		noticeServiceImpl.delete(1);
		noticeServiceImpl.delete(2);
		
		verify(noticeRepository, times(1)).exists(1);
		verify(noticeRepository, times(1)).delete(1);
		verify(noticeRepository, times(0)).delete(2);
	}
	
	@Test
	public void findAllTest() {
		when(noticeServiceImpl.findAll()).thenReturn(notices);
		
		noticeServiceImpl.findAll();
		
		verify(noticeRepository, times(1)).findAll();
	}
	
	@Test
	public void findNoticesOfUser() {
		when(noticeServiceImpl.findNoticesOfUser(user)).thenReturn(notices);
		
		noticeServiceImpl.findNoticesOfUser(user);
		
		verify(noticeRepository, times(1)).findByUser(user);
	}

}
