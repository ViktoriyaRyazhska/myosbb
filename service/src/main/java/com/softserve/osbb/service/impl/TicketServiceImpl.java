package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.enums.TicketState;
import com.softserve.osbb.repository.TicketRepository;
import com.softserve.osbb.service.TicketService;
import com.softserve.osbb.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kris on 11.07.2016.
 */
@Service
public class TicketServiceImpl implements TicketService {


    @Autowired
    TicketRepository ticketRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Ticket save(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Ticket saveAndFlush(Ticket ticket) {
        return ticketRepository.saveAndFlush(ticket);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Ticket findOne(Integer id) {
        return ticketRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Ticket findOne(String id) {
        try {
            return ticketRepository.findOne(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean exists(Integer id) {
        return ticketRepository.exists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long count() {
        return ticketRepository.count();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(Integer id) {
        ticketRepository.delete(id);
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(Ticket ticket) {
        ticketRepository.delete(ticket);
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteAll() {
        ticketRepository.deleteAll();
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void flush() {
        ticketRepository.flush();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Ticket getOne(Integer id) {
        return ticketRepository.getOne(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<Ticket> save(Iterable<Ticket> iterable) {
        return ticketRepository.save(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Ticket update(Ticket ticket) {
        return ticketRepository.exists(ticket.getTicketId()) ? ticketRepository.save(ticket) : null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Ticket> getAllTicketsByTime() {
        return ticketRepository.findByOrderByTimeDesc();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Ticket> getTicketsByName(String name, User user, PageRequest pageRequest) {
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return ticketRepository.findByName(name, pageRequest);
        }
        return ticketRepository.findByNameByOsbb(name, user.getOsbb().getOsbbId(), pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Ticket> getTicketsByState(User user, TicketState ticketState, PageRequest pageRequest) {
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return ticketRepository.findByState(ticketState, pageRequest);
        }
        return ticketRepository.findByStateByOsbb(user.getOsbb().getOsbbId(), ticketState, pageRequest);

    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Ticket> findTicketsByStateAndUser(TicketState state, User user, PageRequest pageRequest) {
        if (state == null) {
            return ticketRepository.findTicketsByUser(user, pageRequest);
        } else {
            return ticketRepository.findTicketsByStateAndUser(state, user.getUserId(), pageRequest);

        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Ticket> findTicketsByStateAndAssign(TicketState state, User user, PageRequest pageRequest) {
        if (state == null) {
            return ticketRepository.findTicketsByAssigned(user, pageRequest);
        } else {
            return ticketRepository.findTicketsByStateAndAssigned(state, user.getUserId(), pageRequest);

        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Ticket> findAllTickets(User user, PageRequest pageRequest) {
        if (user.getRole().getName().equals("ROLE_ADMIN")) {
            return ticketRepository.findAll(pageRequest);
        } else {
            return ticketRepository.findByOsbb(user.getOsbb().getOsbbId(), pageRequest);
        }
    }

}
