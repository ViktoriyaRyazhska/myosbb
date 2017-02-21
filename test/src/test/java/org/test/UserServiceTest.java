package org.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.RoleRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.UserServiceImpl;

public class UserServiceTest {
	
	@Mock
	PasswordEncoder passwordEncoder;
	
	@Mock
	 RoleRepository userRoleRepository;
	
	@Mock
	private User user;
	
	@Mock
	private List<User> list ;
	
	@Mock
	Iterable<Integer> intIteration;
	
	@Mock
	Iterable<User> uIteration;
	
	@Mock
	private Osbb osbbObj;
	
	@Mock
	UserRepository userRep;
	
	@Mock
	Iterable<? extends User> uExIteration;
	
	@Mock
	private Pageable pig;
	
	@Mock
	private Page<User> pUser;
	
	@Mock
	Sort sort;
	
	@Mock
	UserService userService;
	
	
	@InjectMocks
	private UserServiceImpl userServiceImpl= new UserServiceImpl();

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void  saveTest() {
	when(userServiceImpl.save(user)).thenReturn(user);
		when(userService.save(uIteration)).thenReturn(list);
		
		userServiceImpl.save(user);
		userService.save(uIteration);

		verify(userRep, times(1)).save(user);
		verify(userService, times(1)).save(uIteration);
	}
	

	@Test
	public void existsTest() {
		when(userServiceImpl.exists(1)).thenReturn(anyBoolean());
		userServiceImpl.findOne(1);
		verify(userService, times(0)).findOne(1);
	}
	
	@Test
	public void findAllTest() {
		when(userServiceImpl.findAll()).thenReturn(list);
		when(userServiceImpl.findAll(sort)).thenReturn(list);
		when(userServiceImpl.findAll(intIteration)).thenReturn(list);

		userServiceImpl.findAll();
		userServiceImpl.findAll(sort);
		userServiceImpl.findAll(pig);
		userServiceImpl.findAll(intIteration);
		
		verify(userRep, times(1)).findAll();
		verify(userRep, times(1)).findAll(sort);
		verify(userRep, times(1)).findAll(pig);
		verify(userRep, times(1)).findAll(intIteration);
	}
	
	@Test
	public void countTest(){
		userServiceImpl.count();
		verify(userRep, times(1)).count();
	}
	
	@Test
	public void deleteTest() {
		user.setUserId(1);
		 doNothing().when(userService).delete(1);
		 doNothing().when(userService).delete(user);
		 doNothing().when(userService).delete(uExIteration);
		 when(userServiceImpl.exists(user.getUserId())).thenReturn(true);
		 
		 userService.delete(1);
		 userService.delete(user);
		 userService.delete(uExIteration);
		 userServiceImpl.delete(uExIteration);
		 userService.exists(user.getUserId());
     
         verify(userService, times(1)).delete(1);
         verify(userService, times(1)).delete(uExIteration);
	}
	
	@Test
	public void  deleteAllTest() {
		 doNothing().when(userService).deleteAll();
	  	   userServiceImpl.deleteAll();
	}
	
	@Test
	public void flushTest() {
		doNothing().when(userService).flush();
	  	userServiceImpl.flush();
	    verify(userRep, times(1)).flush();
	}
	@Test
	 public void deleteInBatchTest() {
		doNothing().when(userService).deleteInBatch(uIteration);
	  	userServiceImpl.deleteInBatch(uIteration);
	    verify(userRep, times(1)).deleteInBatch(uIteration);
	}
	
	@Test
	 public void deleteAllInBatchTest() {
		doNothing().when(userService).deleteAllInBatch();
	  	userServiceImpl.deleteAllInBatch();
	    verify(userRep, times(1)).deleteAllInBatch();
	}
	
	@Test
	 public void getOneTest() {
		when(userServiceImpl.getOne(1)).thenReturn(user);
		userServiceImpl.getOne(1);
		verify(userRep, times(1)).getOne(1);
	}
	
	@Test
	public void saveAndFlushTest() {
		when(userServiceImpl.saveAndFlush(user)).thenReturn(user);
		userServiceImpl.saveAndFlush(user);
		verify(userRep, times(1)).saveAndFlush(user);
	}
	
	@Test
	public void findUserByEmailTest() {
		when(userService.findUserByEmail("")).thenReturn(user);
		userServiceImpl.findUserByEmail("");
		verify(userRep, times(1)).findUserByEmail("");
	}
	
	@Test
	public void getUsersByOsbbTest() {
		when(userServiceImpl.getUsersByOsbb(osbbObj)).thenReturn(list);
		userServiceImpl.getUsersByOsbb(osbbObj);
		verify(userRep, times(1)).findByOsbb(osbbObj);
	}
	
	@Test
	public void  updateTest() {
		when(userServiceImpl.update(user)).thenReturn(user);
		userServiceImpl.update(user);
		verify(userRep, times(1)).saveAndFlush(user);
	}
	@Test
	public void getCreatorOsbbTest() {
		when(userServiceImpl.getCreatorOsbb(anyInt())).thenReturn(user);
		userServiceImpl.getCreatorOsbb(1);
		verify(userRep, times(1)).getCreatorByOsbbId(1);
	}
}
