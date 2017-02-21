package org.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
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

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.ProviderType;
import com.softserve.osbb.repository.ProviderTypeRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.ProviderTypeService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.ProviderTypeServiceImpl;

public class ProviderTypeServiceTest {

	@Mock
	private ProviderTypeService providerTypeService;
	
	@Mock
	private ProviderType providerType;
	
	@Mock
	ProviderTypeRepository providerTypeRep;
	
	@Mock
	private List<ProviderType> list ;
	
	@InjectMocks
	private ProviderTypeServiceImpl providerTypeServiceImpl= new ProviderTypeServiceImpl();

	@Before
	public void setUp() throws Exception {
		
	MockitoAnnotations.initMocks(this);
	
	}


	@Test
	public void saveProviderTypeTest() throws Exception {
         providerTypeServiceImpl.saveProviderType(providerType);
	}
	
	@Test
	public void findOneProviderTypeByIdTest() throws Exception {
		when(providerTypeServiceImpl.findOneProviderTypeById(1)).thenReturn(providerType);
 	     providerTypeService. findOneProviderTypeById(1);
        verify(providerTypeService, times(1)). findOneProviderTypeById(1);
	}
	
	@Test
	public void findAllProviderTypesTest() throws Exception {
		when(providerTypeServiceImpl.findAllProviderTypes()).thenReturn(list);
	     providerTypeService.findAllProviderTypes();
         verify(providerTypeService, times(1)). findAllProviderTypes();
	}
	@Test
	public void deleteProviderTypeTest() throws Exception {
		 /*doNothing().when(providerTypeService).deleteProviderType(providerType);
	  	 providerTypeService.deleteProviderType(providerType);
	     verify(providerTypeService, times(1)).deleteProviderType(providerType);*/
	     providerTypeServiceImpl.deleteProviderType(providerType);
	}
	@Test
	public void deleteProviderTypeByIdTest() throws Exception { 
		/*	doNothing().when(providerTypeService).deleteProviderTypeById(1);
	  	   providerTypeService.deleteProviderTypeById(1);
	       verify(providerTypeService, times(1)).deleteProviderTypeById(1);*/
	       providerTypeServiceImpl.deleteProviderTypeById(1);
	}
	
	@Test
	public void deleteAllProviderTypesTest() throws Exception {
		/*doNothing().when(providerTypeService).deleteAllProviderTypes();
	  	providerTypeService.deleteAllProviderTypes();
	    verify(providerTypeService, times(1)).deleteAllProviderTypes();*/
	    providerTypeServiceImpl.deleteAllProviderTypes();
	}
	
	@Test
	public void countProviderTypesTest() throws Exception {
		when(providerTypeServiceImpl.countProviderTypes()).thenReturn(1L);
	     providerTypeService.countProviderTypes();
	     verify(providerTypeService, times(1)).countProviderTypes();
	}
	
	@Test
	public void existsProviderTypeTest() throws Exception {
		when(providerTypeServiceImpl.existsProviderType(1)).thenReturn(true);
	     providerTypeService.existsProviderType(1);
	     verify(providerTypeService, times(1)).existsProviderType(1);
	}
	
	@Test
	public void updateProviderTypeTest() throws Exception {
	     providerTypeServiceImpl.updateProviderType(1, providerType);
	}
	
	@Test
	public void findProviderTypesByNameTest() throws Exception {
		when(providerTypeServiceImpl.findProviderTypesByName("name")).thenReturn(list);
	     providerTypeService.findProviderTypesByName("name");
	     verify(providerTypeService, times(1)).findProviderTypesByName("name");
	}
}
