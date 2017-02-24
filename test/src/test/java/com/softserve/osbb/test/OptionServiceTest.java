package com.softserve.osbb.test;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Nazar Kohut
 */

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.softserve.osbb.model.Option;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.Vote;
import com.softserve.osbb.repository.OptionRepository;
import com.softserve.osbb.service.OptionService;
import com.softserve.osbb.service.impl.OptionServiceImpl;

public class OptionServiceTest {

	@InjectMocks
	private OptionServiceImpl optionServiceImpl;
	
	@Mock
	private OptionService optionService;
	
	@Mock
	private OptionRepository optionRepository;
	
	@Mock
	private Option option;
	
	@Mock
	private Vote vote;
	
	@Mock
	private User user;
	
	@Mock
	private List<Option> options;
	
	@Mock
	private List<User> users;
	
	@Before
	public void setUp() throws Exception {
	 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addOptionTest() {
		when(optionServiceImpl.addOption(option)).thenReturn(option);
		
		optionServiceImpl.addOption(option);
		
		verify(optionRepository, times(1)).saveAndFlush(option);
	}
	
	@Test
	public void getOptionTest() {
		when(optionServiceImpl.getOption(1)).thenReturn(option);
		when(optionServiceImpl.getOption(2)).thenReturn(option);
		
		optionServiceImpl.getOption(1);
		optionServiceImpl.getOption(2);
		
		verify(optionRepository, times(1)).findOne(1);
		verify(optionRepository, times(1)).findOne(2);
	}
	
	@Test
	public void getAllOptionTest() {
		when(optionServiceImpl.getAllOption()).thenReturn(options);
		
		optionServiceImpl.getAllOption();
		
		verify(optionRepository, times(1)).findAll();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateOptionTest() {
		Option option = new Option();
		Option option2 = new Option();
		
		option.setOptionId(1);
		option.setDescription("description");
		option.setVote(vote);
		option.setUsers(users);
		
		option2.setOptionId(2);
		
		optionServiceImpl.addOption(option);
		
		when(optionRepository.exists(option.getOptionId())).thenReturn(true);
		when(optionRepository.exists(option2.getOptionId())).thenReturn(false);
		when(optionServiceImpl.updateOption(option)).thenReturn(option);
		
		optionServiceImpl.updateOption(option);
		optionServiceImpl.updateOption(option2);
		optionServiceImpl.updateOption(null);
		
		verify(optionRepository, times(1)).exists(option.getOptionId());
		verify(optionRepository, times(1)).exists(option2.getOptionId());
		verify(optionRepository, times(1)).save(option);
	}
	
	@Test
	public void deleteOptionTest() {
		doNothing().when(optionService).deleteOption(1);
		doNothing().when(optionService).deleteOption(2);
		doNothing().when(optionService).deleteOption(option);
		
		optionServiceImpl.deleteOption(1);
		optionServiceImpl.deleteOption(2);
		optionServiceImpl.deleteOption(option);
		
		verify(optionRepository, times(1)).delete(1);
		verify(optionRepository, times(1)).delete(2);
		verify(optionRepository, times(1)).delete(option);
	}
	
	@Test
	public void deleteAllOptionTest() {
		doNothing().when(optionService).deleteAllOption();
		
		optionServiceImpl.deleteAllOption();
		
		verify(optionRepository, times(1)).deleteAll();
	}

}
