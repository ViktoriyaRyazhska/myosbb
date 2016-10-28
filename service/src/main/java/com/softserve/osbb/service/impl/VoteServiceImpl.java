package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Vote;
import com.softserve.osbb.repository.VoteRepository;
import com.softserve.osbb.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 10.07.2016.
 */
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Vote addVote(Vote vote) {
        return voteRepository.saveAndFlush(vote);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Vote getVoteById(Integer id) {
        return voteRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Vote> getAllAvailable() {
        return voteRepository.findAllAvailable();
    }
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Vote> getAllVotesByDateOfCreation() {
        return voteRepository.findByOrderByStartTimeDesc();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsVote(Integer id) {
        return voteRepository.exists(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteVote(Integer id) {
        voteRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteVote(Vote vote) {
        voteRepository.delete(vote);
    }

}
