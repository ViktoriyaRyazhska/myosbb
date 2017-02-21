package org.test;


import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.repository.OsbbRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.OsbbServiceImpl;

public class OsbbServiceTest {

	
	@Mock
	private Osbb osbb;
	
	@Mock
	private OsbbService osbbService;
	
	@Mock
	private List<Osbb> list ;
	
	@Mock
	private OsbbRepository osbbRep;
	
	@InjectMocks
	private OsbbServiceImpl osbbServiceImpl = new OsbbServiceImpl();


	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void addOsbbTest() {
		/*when(osbbServiceImpl.addOsbb(osbb)).thenReturn(osbb);
		osbbServiceImpl.addOsbb(osbb);
		verify(osbbRep, times(1)).saveAndFlush(osbb);*/
		osbbServiceImpl.addOsbb(osbb);
	}
	
	@Test 
	public void getOsbbTest() {
		when(osbbServiceImpl.getOsbb(1)).thenReturn(osbb);
		osbbServiceImpl.getOsbb(1);
		verify(osbbRep, times(1)).findOne(1);
	}
	
	@Test
	public void getOsbb2Test() {
		when(osbbServiceImpl.getOsbb(anyString())).thenReturn(osbb);
		osbbServiceImpl.getOsbb(anyString());
		verify(osbbRep, times(1)).findByName(anyString());
	}
	
	@Test
	public void getAllOsbbTest() {
		when(osbbServiceImpl.getAllOsbb()).thenReturn(list);
		osbbServiceImpl.getAllOsbb();
		verify(osbbRep, times(1)).findAll();
	}
	
	@Test
	public void getAllByOrderTest() {
		
		when(osbbServiceImpl.getAllByOrder("name", true)).thenReturn(list);
		osbbServiceImpl.getAllByOrder("name", true);
		osbbServiceImpl.getAllByOrder(null, null);
	}
	
	@Test
	public void findByAvailableTest() {
		when(osbbServiceImpl. findByAvailable(anyBoolean())).thenReturn(list);
		osbbServiceImpl. findByAvailable(anyBoolean());
		verify(osbbRep, times(1)). findByAvailable(anyBoolean());
	}
	
	@Test
	public void findByNameContainingTest() {
		when(osbbServiceImpl.findByNameContaining(anyString())).thenReturn(list);

		osbbServiceImpl.findByNameContaining(anyString());

		verify(osbbRep, times(1)).findByNameContainingIgnoreCase(anyString());
	}
	
	@Test
	public void countOsbbTest() {
		osbbServiceImpl.countOsbb();
	}
	@Test
	public void existsOsbbTest() {
		when(osbbServiceImpl.existsOsbb(1)).thenReturn(true);
		osbbServiceImpl.existsOsbb(1);
		verify(osbbRep, times(1)).exists(1);
	}
	
	@Test
	public void  updateOsbbTest() {
		osbbServiceImpl.updateOsbb(osbb);
	}
	
	@Test
	public void deleteOsbbTest() {
		 doNothing().when(osbbService).deleteOsbb(1);
		 doNothing().when(osbbService).deleteOsbb(osbb);
		 
  	   osbbServiceImpl.deleteOsbb(1);
  	   osbbServiceImpl.deleteOsbb(osbb);
  	 
         verify(osbbRep, times(1)).delete(1);
         verify(osbbRep, times(1)).delete(osbb);
	}

}
