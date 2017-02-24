package com.softserve.osbb.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.BarChartData;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.BillChartData;
import com.softserve.osbb.repository.BillRepository;
import com.softserve.osbb.service.BillChartService;
import com.softserve.osbb.service.impl.BillChartServiceImpl;
import com.softserve.osbb.service.impl.BillServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class BillChartServiceTest {
	
	@InjectMocks
	private BillChartServiceImpl billChartServiceImpl;
	
	@Mock
	private BillServiceImpl billServiceImpl;
	
	@Mock
	private BillChartService billChartService;
	
	@Mock
	private BillRepository billRepository;
	
	@Mock
	private Bill bill;
	
	@Mock
	private BillChartData billChartData;
	
	@Mock
	private BarChartData barChartData;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getBillChartDataTest() {		
		billChartServiceImpl.getBillChartData();
		
		verify(billChartData, times(0)).setTotalPercentagePaid(0.0);
	}
	
	@Test
	public void getBarChartDataTest() {
		Bill bill = new Bill();
		
		bill.setBillId(1);
		bill.setDate(null);
		bill.setApartment(null);
		bill.setBillStatus(null);
		bill.setName("bill1");
		bill.setPaid((float) 20.0);
		bill.setParentBill(null);
		bill.setProvider(null);
		bill.setTariff((float) 20.0);
		bill.setToPay((float) 20.0);
		
		billServiceImpl.saveBill(bill);
		
		billChartServiceImpl.getBarChartData(2016);
		
		verify(billChartService, times(0)).getBarChartData(2016);
	}
	
	

}
