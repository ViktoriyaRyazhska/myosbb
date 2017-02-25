package com.softserve.osbb.test;

import static org.mockito.Matchers.anyString;
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
import org.springframework.data.domain.Sort;

import com.softserve.osbb.model.Contract;
import com.softserve.osbb.repository.ContractRepository;
import com.softserve.osbb.service.ContractService;
import com.softserve.osbb.service.impl.ContractServiceImpl;

/**
 * Created by Nazar Kohut
 */

public class ContractSrviceTest {
	
	@InjectMocks
	private ContractServiceImpl contractServiceImpl;
	
	@Mock
	private ContractService contractService;
	
	@Mock
	private ContractRepository contractRepository;
	
	@Mock
	private Contract contract;
	
	@Mock
	private Sort sort;
	
	@Mock
	private Pageable pageable;
	
	@Mock
	private Iterable<Integer> iter;
	
	@Mock
	Iterable<? extends Contract> iterab;
	
	@Mock
	private List<Contract> contracts;
	
	@Mock
	private Iterable<Contract> iterable;
	
	@Mock
	private Page<Contract> pageContract;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveTest() {
		when(contractServiceImpl.save(contract)).thenReturn(contract);
		when(contractServiceImpl.save(iterable)).thenReturn(contracts);
		
		contractServiceImpl.save(contract);
		contractServiceImpl.save(iterable);
		
		verify(contractRepository, times(1)).save(contract);
	}
	
	@Test 
	public void findOneTest() {
		when(contractServiceImpl.findOne(1)).thenReturn(contract);
		when(contractServiceImpl.findOne(2)).thenReturn(contract);
		when(contractServiceImpl.findOne("3")).thenReturn(contract);
		
		contractServiceImpl.findOne(1);
		contractServiceImpl.findOne(2);
		contractServiceImpl.findOne("3");
		contractServiceImpl.findOne("qw");
		
		verify(contractRepository, times(1)).findOne(1);
		verify(contractRepository, times(1)).findOne(2);
		verify(contractRepository, times(1)).findOne(Integer.parseInt("3"));
	}
	
	@Test
	public void existsTest() {
		when(contractServiceImpl.exists(1)).thenReturn(true);
		when(contractServiceImpl.exists(2)).thenReturn(true);
		
		contractServiceImpl.exists(1);
		contractServiceImpl.exists(2);
		
		verify(contractRepository, times(1)).exists(1);
		verify(contractRepository, times(1)).exists(2);
	}
	
	@Test
	public void findAllTest() {
		when(contractServiceImpl.findAll()).thenReturn(contracts);
		when(contractServiceImpl.findAll(sort)).thenReturn(contracts);
		when(contractServiceImpl.findAll(pageable)).thenReturn(pageContract);
		when(contractServiceImpl.findAll(iter)).thenReturn(contracts);
		
		contractServiceImpl.findAll();
		contractServiceImpl.findAll(sort);
		contractServiceImpl.findAll(pageable);
		contractServiceImpl.findAll(iter);
		
		verify(contractRepository, times(1)).findAll();
		verify(contractRepository, times(1)).findAll(sort);
		verify(contractRepository, times(1)).findAll(pageable);
		verify(contractRepository, times(1)).findAll(iter);
	}
	
	@Test
	public void countTest() {		
		contractServiceImpl.count();
		
		verify(contractRepository, times(1)).count();
	}
	
	@Test
	public void deleteTest() {
		doNothing().when(contractService).delete(1);
		doNothing().when(contractService).delete(contract);
		doNothing().when(contractService).delete(iterab);
		
		contractServiceImpl.delete(1);
		contractServiceImpl.delete(contract);
		contractServiceImpl.delete(iterab);
		
		verify(contractRepository, times(1)).delete(1);
		verify(contractRepository, times(1)).delete(contract);
		verify(contractRepository, times(1)).delete(iterab);
	}
	
	@Test
	public void deleteAllTest() {
		doNothing().when(contractService).deleteAll();
		
		contractServiceImpl.deleteAll();
		
		verify(contractRepository, times(1)).deleteAll();
	}
	
	@Test
	public void flushTest() {
		doNothing().when(contractService).flush();
		
		contractServiceImpl.flush();
		
		verify(contractRepository, times(1)).flush();
	}
	
	@Test
	public void deleteInBatchTest() {
		doNothing().when(contractService).deleteInBatch(iterable);
		
		contractServiceImpl.deleteInBatch(iterable);
		
		verify(contractRepository, times(1)).deleteInBatch(iterable);
	}
	
	@Test
	public void deleteAllInBatchTest() {
		doNothing().when(contractService).deleteAllInBatch();
		
		contractServiceImpl.deleteAllInBatch();
		
		verify(contractRepository, times(1)).deleteAllInBatch();
	}
	
	@Test
	public void getOneTest() {
		when(contractServiceImpl.getOne(1)).thenReturn(contract);
		when(contractServiceImpl.getOne(2)).thenReturn(contract);
		
		contractServiceImpl.getOne(1);
		contractServiceImpl.getOne(2);
		
		verify(contractRepository, times(1)).getOne(1);
		verify(contractRepository, times(1)).getOne(2);
	}
	
	@Test
	public void saveAndFlushTest() {
		when(contractServiceImpl.saveAndFlush(contract)).thenReturn(contract);
		
		contractServiceImpl.saveAndFlush(contract);
		
		verify(contractRepository, times(1)).saveAndFlush(contract);
	}
	
	@Test
	public void getContractsTest() {
		when(contractServiceImpl.getContracts(1, "number", true)).thenReturn(pageContract);
		when(contractServiceImpl.getContracts(1, null, true)).thenReturn(pageContract);
		
		contractServiceImpl.getContracts(1, "number", true);
		contractServiceImpl.getContracts(1, null, true);
	}
	
	@Test
	public void findByActiveTrueTest() {
		when(contractServiceImpl.findByActiveTrue(1, "number", true)).thenReturn(pageContract);
		when(contractServiceImpl.findByActiveTrue(1, null, true)).thenReturn(pageContract);
		when(contractServiceImpl.findByActiveTrue()).thenReturn(contracts);
		
		contractServiceImpl.findByActiveTrue(1, "number", true);
		contractServiceImpl.findByActiveTrue(1, null, true);
		contractServiceImpl.findByActiveTrue();
		
		verify(contractRepository, times(1)).findByActiveTrue();
	}
	
	@Test
	public void findContractsByProviderNameTest() {
		when(contractServiceImpl.findContractsByProviderName("Volya")).thenReturn(contracts);
		
		contractServiceImpl.findContractsByProviderName("Volya");
		
		verify(contractRepository, times(1)).findContractsByProviderName(anyString());
	}

}
