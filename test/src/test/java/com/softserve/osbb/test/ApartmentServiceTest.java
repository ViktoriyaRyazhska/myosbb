package com.softserve.osbb.test;
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
import org.springframework.data.domain.PageRequest;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.ApartmentRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class ApartmentServiceTest {
	
	@Mock
    private ApartmentService apartmentService;
	
	@Mock
	private Apartment apartment;
	
	@Mock
	private ApartmentRepository apartmentRepository;
	
	@Mock
	private PageRequest pageRequest;
	
	@Mock
	private Page<Apartment> page;
	
	@Mock
	private List<Apartment> list;
	
	@InjectMocks
	ApartmentServiceImpl apartmentServiceImpl = new ApartmentServiceImpl();
	 
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveTest() {
		Apartment first = new Apartment();
		House house = new House();
		List<User> users = new ArrayList<>();
		Collection<Bill> bills = new ArrayList<>();
		first.setApartmentId(12);
		first.setNumber(12);
		first.setSquare(60);
		first.setHouse(house);
		first.setUsers(users);
		first.setOwner(2);
		first.setBills(bills);		
		
		when(apartmentServiceImpl.save(apartment)).thenReturn(apartment);
		
		apartmentServiceImpl.save(apartment);
		
		verify(apartmentRepository, times(1)).save(apartment);
	} 
	
	@Test
	public void saveApartmentListTest() {
		doNothing().when(apartmentService).saveApartmentList(list);
		
		apartmentServiceImpl.saveApartmentList(list);
		
		verify(apartmentRepository, times(1)).save(list);
	} 
	
	
	@Test
	public void findByIdTest() {	
		when(apartmentServiceImpl.findById(1)).thenReturn(apartment);
		when(apartmentServiceImpl.findById(2)).thenReturn(apartment);
		
		apartmentServiceImpl.findById(1);
		apartmentServiceImpl.findById(2);
		
		verify(apartmentRepository, times(1)).findOne(1);
		verify(apartmentRepository, times(1)).findOne(2);
	}
	
	@Test
	public void findAllTest() {
		when(apartmentServiceImpl.findAll()).thenReturn(list);
		
		apartmentServiceImpl.findAll();
		
		verify(apartmentRepository, times(1)).findAll();
	}
	
	@Test
	public void deleteTest() {
		doNothing().when(apartmentService).delete(apartment);
		
		apartmentServiceImpl.delete(apartment);
		
		verify(apartmentRepository, times(1)).delete(apartment);
	}
	
	@Test
	public void deleteByIdTest() {
		doNothing().when(apartmentService).deleteById(1);
		doNothing().when(apartmentService).deleteById(2);
		
		apartmentServiceImpl.deleteById(1);
		apartmentServiceImpl.deleteById(2);
		
		verify(apartmentRepository, times(1)).delete(1);
		verify(apartmentRepository, times(1)).delete(2);
	}
	
	@Test
	public void updateTest() {
		Apartment first = new Apartment();
		House house = new House();
		List<User> users = new ArrayList<>();
		Collection<Bill> bills = new ArrayList<>();
		first.setApartmentId(1);
		first.setNumber(1);
		first.setSquare(60);
		first.setHouse(house);
		first.setUsers(users);
		first.setOwner(2);
		first.setBills(bills);
		
		when(apartmentServiceImpl.update(apartment)).thenReturn(apartment);
		
		apartmentServiceImpl.update(apartment);
		
		verify(apartmentRepository, times(1)).saveAndFlush(apartment);
	}
	
	@Test
	public void getByPageNumberTest() {
		when(apartmentServiceImpl.getByPageNumber(1, "number", true, 1, 1)).thenReturn(page);
		when(apartmentServiceImpl.getByPageNumber(1, null, true, 1, 1)).thenReturn(page);
		when(apartmentServiceImpl.getByPageNumber(1, "number", true, 1)).thenReturn(page);
		when(apartmentServiceImpl.getByPageNumber(1, null, true, 1)).thenReturn(page);
		
		apartmentServiceImpl.getByPageNumber(1, "number", true, 1, 1);
		apartmentServiceImpl.getByPageNumber(1, "number", true, null, 1);
		apartmentServiceImpl.getByPageNumber(1, null, true, 1, 1);
		apartmentServiceImpl.getByPageNumber(1, "number", true, 1);
		apartmentServiceImpl.getByPageNumber(1, "number", true, null);
		apartmentServiceImpl.getByPageNumber(1, null, true, 1);
		
		verify(apartmentRepository, times(0)).findByNumber(pageRequest, 1, 1);
	}

}
