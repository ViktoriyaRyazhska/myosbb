package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Vote;

/**
 * Created by Roman on 06.07.2016.
 */
@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer> {

     List<Vote> findByOrderByStartTimeDesc();

     @Query("SELECT v FROM  Vote v WHERE v.available = TRUE")
     List<Vote> findAllAvailable();
}
