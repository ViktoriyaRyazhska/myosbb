package com.softserve.osbb.test;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
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
import com.softserve.osbb.model.Vote;
import com.softserve.osbb.repository.VoteRepository;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.VoteService;
import com.softserve.osbb.service.impl.ApartmentServiceImpl;
import com.softserve.osbb.service.impl.VoteServiceImpl;

public class VoteTest {

	@Mock
	VoteService voteService;
	
	@Mock
	private Vote vote;

	@Mock
	private List<Vote> list;

	@Mock
	VoteRepository voteRep;
	
	@InjectMocks
	private VoteServiceImpl voteServiceImpl = new VoteServiceImpl();

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void addVoteTest() {
		vote.setAvailable(true);
		when(voteServiceImpl.addVote(vote)).thenReturn(vote);
		voteServiceImpl.addVote(vote);
		verify(voteRep).saveAndFlush(vote);
	}
	@Test
	public void getVoteByIdTest() { 
		vote.setVoteId(1);
		when(voteServiceImpl.getVoteById(1)).thenReturn(vote);
		voteServiceImpl.getVoteById(1);
		verify(voteRep).findOne(1);
	}
	
	@Test
	 public void getAllAvailableTest() {
		when(voteServiceImpl.getAllAvailable()).thenReturn(list);
		voteServiceImpl.getAllAvailable();
		verify(voteRep).findAllAvailable();
	}
	
	@Test
	 public void getAllVotesByDateOfCreationTest(){
		when(voteServiceImpl.getAllVotesByDateOfCreation()).thenReturn(list);
		voteService.getAllVotesByDateOfCreation();
		verify(voteService, times(1)).getAllVotesByDateOfCreation();
	}
	
	@Test
	public void existsVoteTest() {
		when(voteServiceImpl.existsVote(1)).thenReturn(true);
		voteService.existsVote(1);
		verify(voteService, times(1)).existsVote(1);
	}
	
	@Test
	public void deleteVoteTest() {
		 doNothing().when(voteRep).delete(1);
		 doNothing().when(voteRep).delete(vote);
	     voteServiceImpl.deleteVote(1);
	     voteServiceImpl.deleteVote(vote);
	     verify(voteService, times(0)).deleteVote(1);
	     verify(voteService, times(0)).deleteVote(vote);
	     
	 	Integer i=null;
		doThrow(new IllegalArgumentException()).when(voteRep).delete(i);
		voteService.deleteVote(1);
	}

}

