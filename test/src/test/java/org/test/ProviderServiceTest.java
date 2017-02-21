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
import org.springframework.data.domain.Page;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Provider;
import com.softserve.osbb.repository.ProviderRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.ProviderService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.ProviderServiceImpl;

public class ProviderServiceTest {

	@Mock
	private ProviderService providerService;

	@Mock
	private Provider provider;
	
	@Mock
	private ProviderRepository providerRep;

	@Mock
	private List<Provider> list;

	@Mock
	private List<Integer> iList;
	
	@Mock
	private Page<Provider> pageProvider;
	
	@InjectMocks
	private ProviderServiceImpl providerServiceImpl = new ProviderServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveProviderTest() {
		/*doNothing().when(providerService).saveProvider(provider);
		providerService.saveProvider(provider);
		verify(providerService, times(1)).saveProvider(provider);*/
		providerServiceImpl.saveProvider(provider);
	}

	@Test
	public void saveProviderListTest() {
	/*	doNothing().when(providerService).saveProviderList(list);
		providerService.saveProviderList(list);
		verify(providerService, times(1)).saveProviderList(list);*/
		
		providerServiceImpl.saveProviderList(list);
	}

	@Test
	public void findOneProviderByIdTest() {
		when(providerServiceImpl.findOneProviderById(1)).thenReturn(provider);
		providerServiceImpl.findOneProviderById(1);
		verify(providerRep, times(1)).findOne(1);
	}

	@Test
	public void findAllProvidersByIdsTest() {
		when(providerServiceImpl.findAllProvidersByIds(iList)).thenReturn(list);
		providerServiceImpl.findAllProvidersByIds(iList);
		verify(providerRep, times(1)).findAll(iList);
	}

	@Test
	public void findAllProvidersTest() {
		when(providerServiceImpl.findAllProviders()).thenReturn(list);
		providerServiceImpl.findAllProviders();
		verify(providerRep, times(1)).findAll();
	}

	@Test
	public void deleteProviderTest() {
		/*doNothing().when(providerService).deleteProvider(provider);
		providerService.deleteProvider(provider);
		verify(providerService, times(1)).deleteProvider(provider);*/
		providerServiceImpl.deleteProvider(provider);
	}

	@Test
	public void deleteProviderByIdTest() {
		/*doNothing().when(providerService).deleteProviderById(1);
		providerService.deleteProviderById(1);
		verify(providerService, times(1)).deleteProviderById(1);*/
		providerServiceImpl.deleteProviderById(1);
	}

	@Test
	public void deleteListOfProvidersTest() {
		/*doNothing().when(providerService).deleteListOfProviders(list);
		providerService.deleteListOfProviders(list);
		verify(providerService, times(1)).deleteListOfProviders(list);*/
		providerServiceImpl.deleteListOfProviders(list);
	}

	@Test
	public void deleteAllProvidersTest() {
		/*doNothing().when(providerService).deleteAllProviders();
		providerService.deleteAllProviders();
		verify(providerService, times(1)).deleteAllProviders();*/
		providerServiceImpl.deleteAllProviders();
	}

	@Test
	public void countProvidersTest() {
		providerServiceImpl.countProviders();
	}

	@Test
	public void existsProviderTest() {
		when(providerServiceImpl.existsProvider(1)).thenReturn(true);
		providerServiceImpl.existsProvider(1);
		verify(providerRep, times(1)).exists(1);
	}

	@Test
	public void updateProviderTest() throws Exception {
		providerServiceImpl.updateProvider(1, provider);
	}

	@Test
	public void findProvidersByNameOrDescriptionTest() {
		when(providerServiceImpl.findProvidersByNameOrDescription("name")).thenReturn(list);
	}
	
	@Test
	public void getProvidersTest() {
		when(providerServiceImpl.getProviders(1, "name", true)).thenReturn(pageProvider);
		when(providerServiceImpl.getProviders(1, null, true)).thenReturn(null);
		providerServiceImpl.getProviders(1, "name", true);
	}
	
	@Test
	public void findByActiveTrueTest() {
		when(providerServiceImpl.findByActiveTrue(1, "name", true)).thenReturn(pageProvider);
		when(providerServiceImpl.findByActiveTrue(1, null, true)).thenReturn(null);
		when(providerServiceImpl.findByActiveTrue()).thenReturn(list);
		
		providerServiceImpl. findByActiveTrue(1, "name", true);
		providerServiceImpl.findByActiveTrue();
	}

}
