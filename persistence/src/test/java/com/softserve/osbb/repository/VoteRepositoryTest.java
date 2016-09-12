package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceConfiguration;
import com.softserve.osbb.model.Option;
import com.softserve.osbb.model.Vote;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.junit.Assert.*;


/**
 * Created by Roman on 06.07.2016.
 */

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceConfiguration.class)
@Transactional
public class VoteRepositoryTest {

    private Vote vote;
    private Option option1;
    private Option option2;

    private Vote vote1;
    private Option option3;
    private Option option4;

    @Autowired
    VoteRepository voteRepository;

    @Before
    public void init() {
        vote = new Vote();
        option1 = new Option();
        option1.setDescription("option1");
        option1.setVote(vote);
        option2 = new Option();
        option2.setDescription("option2");
        option2.setVote(vote);
        vote.setDescription("Are you ready?");
        vote.setOptions(Arrays.asList(option1, option2));

        vote1 = new Vote();
        option3 = new Option();
        option3.setDescription("option3");
        option3.setVote(vote1);
        option4 = new Option();
        option4.setDescription("option4");
        option4.setVote(vote1);
        vote1.setDescription("How old are you?");
        vote1.setAvailable(false);
        vote1.setOptions(Arrays.asList(option3, option4));
    }

    @Test
    public void testSave() {
        Vote savedVote = voteRepository.save(vote);
        assertNotNull(savedVote);
        assertTrue(voteRepository.exists(savedVote.getVoteId()));
    }

    @Test
    public void testFindAllAvailable() {
        voteRepository.deleteAll();
        voteRepository.save(vote);
        voteRepository.save(vote1);
        assertFalse(voteRepository.findAll().size() < 2);
        assertEquals(1, voteRepository.findAllAvailable().size());
    }

    @Test
    public void testGetVoteById() {
        Vote savedVote = voteRepository.save(vote);
        assertEquals(savedVote.getVoteId(), voteRepository.getOne(savedVote.getVoteId()).getVoteId());
    }

    @Test
    public void testDeleteByVote() {
        vote = voteRepository.save(vote);
        voteRepository.delete(vote);
        assertFalse(voteRepository.exists(vote.getVoteId()));
    }

    @Test
    public void testDeleteByVoteId() {
        vote = voteRepository.save(vote);
        voteRepository.delete(vote.getVoteId());
        assertFalse(voteRepository.exists(vote.getVoteId()));
    }

    @Test
    public void testDeleteAllVotes(){
        voteRepository.deleteAll();
        assertTrue(voteRepository.findAll().isEmpty());
    }
}
