package com.softserve.osbb.repository;

import com.softserve.osbb.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by nataliia on 05.07.16.
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    @Query("select e from Event e where lower(e.title) like lower(concat('%',:search,'%'))"
            + " or e.author in (select u.userId from User u "
            + " where lower(u.firstName) like lower(concat('%',:search,'%')) "
            + " or lower(u.lastName) like lower(concat('%',:search,'%')))"
            + " or lower(e.description) like lower(concat('%',:search,'%'))")
    List<Event> findByTitleOrAuthorOrDescription(
            @Param("search") String search);

    @Query("select e from Event e where e.startTime between :startTime and :endTime"
            + " or e.endTime between :startTime and :endTime")
    List<Event> findBetweenStartTimeAndEndTime(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);

    @Query("select e from Event e where e.author = (select u.userId from User u where u.email = :email)")
    List<Event> findByAuthorEmail(@Param("email") String email);
    
}
