package com.softserve.osbb.service;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Report;
import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.enums.TicketState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Kris on 12.07.2016.
 */

@Service
public interface TicketService {

    Ticket save(Ticket ticket);

    Ticket saveAndFlush(Ticket ticket);

    Ticket findOne(Integer id);

    Ticket findOne(String id);

    boolean exists(Integer id);

    long count();

    boolean delete(Integer id);

    boolean delete(Ticket ticket);

    boolean deleteAll();

    void flush();

    Ticket getOne(Integer id);

    List<Ticket> save(Iterable<Ticket> iterable);

    Ticket update(Ticket ticket);

    List<Ticket> getAllTicketsByTime();

    Page<Ticket> getTicketsByName(String name, User user, PageRequest pageRequest);

    Page<Ticket> getTicketsByState(User user, TicketState ticketState, PageRequest pageRequest);

    Page<Ticket> findTicketsByStateAndUser(TicketState state, User user, PageRequest pageRequest);

    Page<Ticket> findTicketsByStateAndAssign(TicketState state, User user, PageRequest pageRequest);

    Page<Ticket> findAllTickets(User user, PageRequest pageRequest);


}
