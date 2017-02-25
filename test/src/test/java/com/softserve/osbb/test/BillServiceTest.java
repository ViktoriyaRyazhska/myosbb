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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.ApartmentRepository;
import com.softserve.osbb.repository.BillRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.BillService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.impl.BillServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class BillServiceTest {
	
	@InjectMocks
	private BillServiceImpl billServiceImpl;
	
	@Mock
	private BillService billService;
	
	@Mock
	private UserService userService;
	
	@Mock
	private Bill bill;
	
	@Mock
	private Pageable pageable;
	
	@Mock
	private User user;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private BillRepository billRepository;
	
	@Mock
	private ApartmentRepository apartmentRepository;
	
	@Mock
	private List<Bill> bills;
	
	@Mock
	private Page<Bill> pageBill;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveBillTest() {
		when(billServiceImpl.saveBill(bill)).thenReturn(bill);
		
		billServiceImpl.saveBill(bill);
		
		verify(billRepository, times(1)).save(bill);
	}
	
	@Test
	public void saveBillListTest() {
		doNothing().when(billService).saveBillList(bills);
		
		billServiceImpl.saveBillList(bills);
		
		verify(billRepository, times(1)).save(bills);
	}
	
	@Test
	public void findOneBillByIDTest() {
		when(billServiceImpl.findOneBillByID(1)).thenReturn(bill);
		when(billServiceImpl.findOneBillByID(2)).thenReturn(bill);
		
		billServiceImpl.findOneBillByID(1);
		billServiceImpl.findOneBillByID(2);
		
		verify(billRepository, times(1)).findOne(1);
		verify(billRepository, times(1)).findOne(2);
	}
	
	@Test
	public void findAllBillsByIDsTest() {
		when(billServiceImpl.findAllBillsByIDs(null)).thenReturn(bills);
		
		billServiceImpl.findAllBillsByIDs(null);
		
		verify(billService, times(0)).findAllBillsByIDs(null);
	}
	
	@Test
	public void findAllBillsTest() {
		when(billServiceImpl.findAllBills(pageable)).thenReturn(pageBill);
		
		billServiceImpl.findAllBills(pageable);
		
		verify(billRepository, times(1)).findAll(pageable);
	}
	
	@Test
	public void findAllByUserIdTest() {
		when(billServiceImpl.findAllByUserId(1)).thenReturn(bills);
		when(billServiceImpl.findAllByUserId(2)).thenReturn(bills);
		
		billServiceImpl.findAllByUserId(1);
		billServiceImpl.findAllByUserId(2);
		
		verify(billRepository, times(1)).findAllByUserId(1);
		verify(billRepository, times(1)).findAllByUserId(2);
	}
	
	@Test
	public void findAllByApartmentOwnerTest() {
		Apartment ownersApartment = new Apartment();
		User apartmentOwner = new User();
		 
		apartmentOwner.setUserId(1); 
		
		when(billRepository.findByApartment(ownersApartment, pageable)).thenReturn(pageBill);
		when(billServiceImpl.findAllByApartmentOwner(1, pageable)).thenReturn(pageBill);
		when(billServiceImpl.findAllByApartmentOwner(2, pageable)).thenReturn(pageBill);
		
		verify(billRepository, times(0)).findByApartment(ownersApartment, pageable);
	}
	
	@Test
	public void deleteBillTest() {
		doNothing().when(billService).deleteBill(bill);
		
		billServiceImpl.deleteBill(bill);
		
		verify(billRepository, times(1)).delete(bill);
	}
	
	@Test
	public void deleteBillByIDTest() {
		when(billServiceImpl.deleteBillByID(1)).thenReturn(true);
		when(billServiceImpl.deleteBillByID(2)).thenReturn(true);
		
		billServiceImpl.deleteBillByID(1);
		billServiceImpl.deleteBillByID(2);
		
		verify(billRepository, times(1)).delete(1);
		verify(billRepository, times(1)).delete(2);
	}
	
	@Test
	public void deleteListBillsTest() {
		doNothing().when(billService).deleteListBills(bills);
		
		billServiceImpl.deleteListBills(bills);
		
		verify(billRepository, times(1)).delete(bills);
	}
	
	@Test
	public void deleteAllBillsTest() {
		doNothing().when(billService).deleteAllBills();
		
		billServiceImpl.deleteAllBills();
		
		verify(billRepository, times(1)).deleteAll();
	}
	
	@Test
	public void countBillsTest() {		
		billServiceImpl.countBills();
		
		verify(billRepository, times(1)).count();
	}
	
	@Test
	public void existsBillTest() {
		when(billServiceImpl.existsBill(1)).thenReturn(true);
		when(billServiceImpl.existsBill(2)).thenReturn(true);
		
		billServiceImpl.existsBill(1);
		billServiceImpl.existsBill(2);
		
		verify(billRepository, times(1)).exists(1);
		verify(billRepository, times(1)).exists(2);
	}
	
	@Test
	public void getAllBillsByApartmentWithCurrentMonthTest() {
		when(billServiceImpl.getAllBillsByApartmentWithCurrentMonth(1)).thenReturn(bills);
		when(billServiceImpl.getAllBillsByApartmentWithCurrentMonth(2)).thenReturn(bills);
		
		billServiceImpl.getAllBillsByApartmentWithCurrentMonth(1);
		billServiceImpl.getAllBillsByApartmentWithCurrentMonth(2);
		
		verify(billRepository, times(1)).getAllBillsByApartmentWithCurrentMonth(1);
		verify(billRepository, times(1)).getAllBillsByApartmentWithCurrentMonth(2);
	}
	
	@Test
	public void findAllParentBillsTest() {
		when(billServiceImpl.findAllParentBills(pageable)).thenReturn(pageBill);
		
		billServiceImpl.findAllParentBills(pageable);
		
		verify(billRepository, times(1)).findByParentBillIsNull(pageable);
	}
	
	@Test
	public void findAllParentBillIdTest() {
		when(billServiceImpl.findAllParentBillId()).thenReturn(bills);
		
		billServiceImpl.findAllParentBillId();
		
		verify(billRepository, times(1)).findByParentBillIsNotNull();
	}
	
	@Test
	public void findAllParentBillByIdTest() {
		when(billServiceImpl.findAllParentBillById(1)).thenReturn(bills);
		when(billServiceImpl.findAllParentBillById(2)).thenReturn(bills);
		
		billServiceImpl.findAllParentBillById(1);
		billServiceImpl.findAllParentBillById(2);
		
		verify(billRepository, times(1)).findParentBillById(1);
		verify(billRepository, times(1)).findParentBillById(2);
	}

}
