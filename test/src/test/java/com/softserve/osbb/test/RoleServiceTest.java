package com.softserve.osbb.test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
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
import org.springframework.data.domain.PageRequest;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Role;
import com.softserve.osbb.repository.RoleRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.RoleService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.RoleServiceImpl;


public class RoleServiceTest {

	@Mock
	private RoleService roleService;
	
	@Mock
	private Role role;
	
	@Mock
	Page<Role> pRole;
	
	@Mock
	private List<Role> list ;
	
	@Mock
	RoleRepository roleRep;
	
	@Mock
	PageRequest pageReq;
	
	@InjectMocks
	private RoleServiceImpl roleServiceImpl;
	

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void  addRoleTest() {
		when(roleServiceImpl.addRole(role)).thenReturn(role);
		roleService.addRole(role);
	}
	
	@Test
     public void getRoleTest() {
		
		when(roleServiceImpl.getRole(1)).thenReturn(role);
		when(roleServiceImpl.getRole(anyString())).thenReturn(role);
		
		roleServiceImpl.getRole(1);
		
		verify(roleRep).findOne(1);
		verify(roleRep, times(0)).findByName(anyString());
	}
	
	@Test
	public void getAllRoleTest() {
		when(roleServiceImpl.getAllRole()).thenReturn(list);
		when(roleServiceImpl.getAllRole(2, "name", true)).thenReturn(pRole);
		  roleServiceImpl.getAllRole();
	}
	
	@Test
	public void  countRoleTest() {
	when(roleServiceImpl.countRole()).thenReturn(1L);
	roleServiceImpl.countRole();
	verify(roleRep, times(1)).count();
	}
	
	@Test
	public void  existsRoleTest() {
		when(roleServiceImpl.existsRole(2)).thenReturn(true);	
		roleServiceImpl.existsRole(2);
		verify(roleRep, times(1)).exists(2);

	}
	
	@Test
	public void updateRoleTest() {
		when(roleServiceImpl.updateRole(role)).thenReturn(role);
		roleServiceImpl.updateRole(role);
		verify(roleRep, times(1)).save(role);
	}
	
	@Test
	public void deleteRoleTest() {
		doNothing().when(roleService).deleteRole(1);
		doNothing().when(roleService).deleteRole(role);
		roleServiceImpl.deleteRole(1);
		roleServiceImpl.deleteRole(role);
	};
	
	@Test
	public void deleteAllRoleTest() {
		roleServiceImpl.deleteAllRole();
	}
}
	

