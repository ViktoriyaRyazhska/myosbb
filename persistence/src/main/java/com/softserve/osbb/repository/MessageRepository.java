package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.Ticket;

/**
 * Created by Kris on 05.07.2016.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    Page<Message> findByTicketOrderByTimeDesc(Ticket ticket, Pageable pageable);

    List<Message> findByParentIdOrderByTimeDesc(Integer id);
}
