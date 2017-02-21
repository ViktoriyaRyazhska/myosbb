package org.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
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
import org.springframework.data.domain.PageRequest;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.ProviderType;
import com.softserve.osbb.model.Report;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.ReportRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.ReportService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.ReportServiceImpl;

public class ReportServiceTest {
	
	@Mock
	private ReportService reportService;
	
	@Mock
	private ReportRepository reportRep;
	
	@Mock
	 private UserRepository userRep;
	
	@Mock
	private Report report;
	
	@Mock
	private List<Report> list;
	
	@Mock
	private ProviderType providertype;
	
	@Mock
	private PageRequest pageRequest;
	
	@Mock
	private Page<Report> pReport;
	
	@Mock
	private List<LocalDate> dateList;
	
	@Mock
	private List<Report> EMPTY_LIST;
	
	@Mock
	PageRequest pageReq;
	
	@Mock
	User user;
	
	@InjectMocks
	private ReportServiceImpl reportServiceImpl= new ReportServiceImpl();

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
	
	}

	@Test
	public void addReportTest()throws Exception{
		reportServiceImpl.addReport(report);
	}
	
	@Test
	public void updateReportTest() throws Exception {
		reportServiceImpl.updateReport(1, report);
	}
	
	@Test
	public void getReportByIdTest() {
		
		when(reportServiceImpl. getReportById(1)).thenReturn(report);
		reportServiceImpl.getReportById(1);
		verify(reportRep, times(1)).findOne(1);
	}
	
	@Test
	public void getOneReportBySearchTermTest() throws Exception {
		
		when(reportService.getOneReportBySearchTerm(anyString()))
                                                                     .thenReturn(report);
		reportServiceImpl.getOneReportBySearchTerm("");
		verify(reportRep, times(1)).getAllReportsBySearchParam("");
	}
	
	
	@Test
	public void getAlReportsBySearchParameterTest() throws Exception {
	
		when(reportService.getAlReportsBySearchParameter("")).thenReturn(list);
		when(reportService.getAlReportsBySearchParameter(null)).thenReturn(null);
		
		reportServiceImpl.getAlReportsBySearchParameter("");
		reportServiceImpl.getAlReportsBySearchParameter(null);
	}
	@Test
	public void getAllReportsByUserAndSearchParameterTest() {
	when(reportServiceImpl.getAllReportsByUserAndSearchParameter(1, anyString())).thenReturn(list);
	}
	@Test
	public void getAllReportsTest() {
		
		when(reportServiceImpl.getAllReports()).thenReturn(list);
		reportServiceImpl.getAllReports(pageRequest);
		verify(reportRep, times(1)).findAll(pageRequest);
	}
	@Test
	public void getAllUserReportsTest() {
		when(reportServiceImpl.getAllUserReports(1, pageRequest)).thenReturn(pReport);
		reportServiceImpl.getAllUserReports(1, pageRequest);
	}
	@Test
	public void findDistinctCreationDatesTest() {
		
		when(reportServiceImpl.findDistinctCreationDates()).thenReturn(dateList);
		reportServiceImpl.findDistinctCreationDates();
		verify(reportRep).findDistinctCreationDates();
	}
	@Test
	public void deleteAllTest() throws Exception{
		reportServiceImpl.deleteAll();
	}
	
	@Test
	public void deleteReportById() throws Exception{
		when(reportServiceImpl.deleteReportById(1)).thenReturn(true);
		reportServiceImpl.deleteReportById(1);
		verify(reportRep).delete(1);
	}
	
	@Test
	public void getAllReports() {
		when(reportServiceImpl.getAllReports()).thenReturn(list);
		when(reportServiceImpl.getAllReports(pageReq)).thenReturn(pReport);
		
		reportServiceImpl.getAllReports();
		verify(reportRep, times(1)).findAll();
	}
	  
}
