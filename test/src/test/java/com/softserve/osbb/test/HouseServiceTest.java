package com.softserve.osbb.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Street;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.HouseRepository;
import com.softserve.osbb.service.HouseService;
import com.softserve.osbb.service.impl.HouseServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class HouseServiceTest {

	@InjectMocks
	private HouseServiceImpl houseServiceImpl;
	
	@Mock
	private HouseService houseService;
	
	@Mock
	private House house;
	
	@Mock
	private HouseRepository houseRepository;
	
	@Mock
	private Apartment apartment;
	
	@Mock
	private Pageable pageable;
	
	@Mock
	private Osbb osbb;
	
	@Mock
	private List<House> houses;
	
	@Mock
	private List<Apartment> apartments;
	
	@Mock
	private Page<House> housePage;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addHouseTest() {
		
		House house = new House();
		House house1 = null;
		Street street = new Street();
		Collection<Apartment> apart = new ArrayList<>();
		Collection<User> users = new ArrayList<>();
		
		house.setHouseId(50);
		house.setNumberHouse(70);
		house.setZipCode("79021");
		house.setDescription("a nice house");
		house.setApartments(apart);
		house.setOsbb(osbb);
		house.setStreet(street);
		house.setUsers(users);
		
		when(houseServiceImpl.addHouse(house)).thenReturn(house);
		
		houseServiceImpl.addHouse(house);
		houseServiceImpl.addHouse(null);
		
		assertNull(house1);
		assertNotNull(house);
		assertEquals((Integer)50, (Integer)house.getHouseId());
		
		verify(houseRepository, times(1)).save(house);
	}
	
	@Test
	public void updateHouseTest() {
		House house = new House();
		Street street = new Street();
		Collection<Apartment> apart = new ArrayList<>();
		Collection<User> users = new ArrayList<>();
		
		house.setHouseId(50);
		house.setNumberHouse(70);
		house.setZipCode("79021");
		house.setDescription("a nice house");
		house.setApartments(apart);
		house.setOsbb(osbb);
		house.setStreet(street);
		house.setUsers(users);
		
		houseServiceImpl.addHouse(house);
		
		when(houseServiceImpl.updateHouse(1, house)).thenReturn(house);
		
		houseServiceImpl.updateHouse(1, house);
		houseServiceImpl.updateHouse(2, null);
		
		verify(houseService, times(0)).updateHouse(1, house);
	}
	
	@Test
	public void findHouseByIdTest() {
		House house = new House();
		Street street = new Street();
		Collection<Apartment> apart = new ArrayList<>();
		Collection<User> users = new ArrayList<>();
		
		house.setHouseId(1);
		house.setNumberHouse(70);
		house.setZipCode("79021");
		house.setDescription("a nice house");
		house.setApartments(apart);
		house.setOsbb(osbb);
		house.setStreet(street);
		house.setUsers(users);
		
		houseServiceImpl.addHouse(house);
		
		when(houseRepository.exists(1)).thenReturn(true);
		when(houseRepository.exists(2)).thenReturn(false);
		when(houseServiceImpl.findHouseById(1)).thenReturn(house);
		
		houseServiceImpl.findHouseById(1);
		houseServiceImpl.findHouseById(2);
		
		verify(houseRepository, times(2)).exists(1);
		verify(houseRepository, times(1)).exists(2);
		verify(houseRepository, times(1)).findOne(1);
		verify(houseRepository, times(0)).findOne(2);
	}
	
	@Test
	public void getAllHousesBySearchParameterTest() {
		when(houseServiceImpl.getAllHousesBySearchParameter("house1")).thenReturn(houses);
		
		houseServiceImpl.getAllHousesBySearchParameter("house1");
		houseServiceImpl.getAllHousesBySearchParameter(null);
		houseServiceImpl.getAllHousesBySearchParameter("");
		
		verify(houseRepository, times(1)).getAlReportsBySearchParameter(anyString());
	}
	
	@Test
	public void findAllTest() {
		when(houseServiceImpl.findAll()).thenReturn(houses);
		
		houseServiceImpl.findAll();
		
		verify(houseRepository, times(1)).findAll();
	}
	
	@Test
	public void findAllByStreetIdTest() {
		when(houseServiceImpl.findAllByStreetId(1)).thenReturn(houses);
		when(houseServiceImpl.findAllByStreetId(2)).thenReturn(houses);
		
		houseServiceImpl.findAllByStreetId(1);
		houseServiceImpl.findAllByStreetId(2);
		
		verify(houseRepository, times(1)).findByStreetId(1);
		verify(houseRepository, times(1)).findByStreetId(2);
	}
	
	@Test
	public void findAllApartmentsByHouseIdTest() {
		House house = new House();
		House house2 = new House();
		Street street = new Street();
		Collection<Apartment> apart = new ArrayList<>();
		Collection<User> users = new ArrayList<>();
		
		house.setHouseId(1);
		house.setNumberHouse(70);
		house.setZipCode("79021");
		house.setDescription("a nice house");
		house.setApartments(apart);
		house.setOsbb(osbb);
		house.setStreet(street);
		house.setUsers(users);
		
		house2.setHouseId(2);
		house2.setNumberHouse(70);
		house2.setZipCode("79021");
		house2.setDescription("a nice house");
		house2.setApartments(null);
		house2.setOsbb(osbb);
		house2.setStreet(street);
		house2.setUsers(users);
		
		houseServiceImpl.addHouse(house);
		houseServiceImpl.addHouse(house2);
		
		houseServiceImpl.findAllApartmentsByHouseId(house.getHouseId());
		houseServiceImpl.findAllApartmentsByHouseId(house2.getHouseId());
		houseServiceImpl.findAllApartmentsByHouseId(3);
		
		verify(houseRepository, times(1)).save(house);
		verify(houseRepository, times(1)).save(house2);
	}
	
	@Test
	public void deleteHouseByIdTest() {
		when(houseRepository.exists(3)).thenReturn(false);
		when(houseServiceImpl.deleteHouseById(1)).thenReturn(true);
		when(houseServiceImpl.deleteHouseById(400)).thenReturn(false);
		
		houseServiceImpl.deleteHouseById(1);
		houseServiceImpl.deleteHouseById(2);
		
		verify(houseRepository, times(1)).delete(1);
		verify(houseRepository, times(0)).delete(2);
	}
	
	@Test
	public void deleteAllHousesTest() {
		doNothing().when(houseService).deleteAllHouses();
		
		houseServiceImpl.deleteAllHouses();
		
		verify(houseRepository, times(1)).deleteAll();
	}
	
	@Test
	public void getAllHousesTest() {
		when(houseServiceImpl.getAllHouses(pageable)).thenReturn(housePage);
		
		houseServiceImpl.getAllHouses(pageable);
		
		verify(houseRepository, times(1)).findAll(pageable);
	}
	
	@Test
	public void getAllHousesByOsbbTest() {
		when(houseServiceImpl.getAllHousesByOsbb(1)).thenReturn(houses);
		when(houseServiceImpl.getAllHousesByOsbb(2)).thenReturn(houses);
		when(houseServiceImpl.getAllHousesByOsbb(osbb, pageable)).thenReturn(housePage);
		
		houseServiceImpl.getAllHousesByOsbb(1);
		houseServiceImpl.getAllHousesByOsbb(2);
		houseServiceImpl.getAllHousesByOsbb(osbb, pageable);
		
		verify(houseRepository, times(1)).findByOsbbId(1);
		verify(houseRepository, times(1)).findByOsbbId(2);
		verify(houseRepository, times(1)).findByOsbb(osbb, pageable);
	}
	
	@Test
	public void getByNumberHouseAndStreetTest() {
		when(houseServiceImpl.getByNumberHouseAndStreet(1, 1)).thenReturn(house);
		
		houseServiceImpl.getByNumberHouseAndStreet(1, 1);
		
		verify(houseRepository, times(1)).getByNumberHouseAndStreet(1, 1);
	}

}
