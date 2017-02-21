package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     
     @Query(value="SELECT * FROM  Vote v WHERE v.ticket_id = :ticket_id", nativeQuery = true)
     List<Vote> findByTicketId(@Param("ticket_id") Integer ticket_id);
     
     @Query(value="SELECT * FROM  Vote v WHERE v.available = TRUE and v.ticket_id = :ticket_id ",nativeQuery = true)
     List<Vote> findByTicketIdAndAvailable(@Param("ticket_id") Integer ticket_id);
}
