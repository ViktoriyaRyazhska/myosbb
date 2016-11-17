package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.enums.TicketState;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findByOrderByTimeDesc();

    @Query("Select t From Ticket t where (lower(t.name) like lower(concat('%',?1,'%') )"
            + " or lower(t.description) like lower(concat('%',?1,'%') ))"
            + "and t.user.osbb.osbbId=?2 ")
    Page<Ticket> findByNameByOsbb(String name, Integer osbbId, Pageable pageRequest);

    @Query("Select t From Ticket t where lower(t.name) like lower(concat('%',?1,'%') )"
            + " or lower(t.description) like lower(concat('%',?1,'%') )")
    Page<Ticket> findByName(String name, Pageable pageRequest);

    @Query("select t from Ticket t where t.state=:state and t.user.osbb.osbbId=:osbbId ")
    Page<Ticket> findByStateByOsbb(@Param("osbbId") Integer osbbId,
            @Param("state") TicketState state, Pageable pageRequest);

    Page<Ticket> findTicketsByUser(User user, Pageable pageRequest);

    @Query("select t from Ticket t where t.state=:state and t.user.userId=:userId ")
    Page<Ticket> findTicketsByStateAndUser(@Param("state") TicketState state,
            @Param("userId") Integer userId, Pageable pageRequest);

    @Query("select t from Ticket t where t.state=:state and t.assigned.userId=:userId ")
    Page<Ticket> findTicketsByStateAndAssigned(
            @Param("state") TicketState state, @Param("userId") Integer userId,
            Pageable pageRequest);

    @Query("select t from Ticket t join t.user u join u.osbb o where o.osbbId=:osbbId")
    Page<Ticket> findByOsbb(@Param("osbbId") Integer osbbId, Pageable pageable);

    Page<Ticket> findTicketsByAssigned(User user, Pageable pageRequest);

    Page<Ticket> findByState(TicketState state, Pageable pageRequest);

    Page<Ticket> findAll(Pageable pageable);

}
