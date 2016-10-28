package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.Ticket;
import com.softserve.osbb.repository.MessageRepository;
import com.softserve.osbb.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kris on 10.07.2016.
 */
@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Message findOne(Integer id) {
        return messageRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Message findOne(String id) {
        try {
            return messageRepository.findOne(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean exists(Integer id) {
        return messageRepository.exists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Message> findAll(Sort sort) {
        return messageRepository.findAll(sort);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Message> findAll(Iterable<Integer> iterable) {
        return messageRepository.findAll(iterable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long count() {
        return messageRepository.count();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Integer integer) {
        if (exists(integer)) {
            messageRepository.delete(integer);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Message message) {
        if (exists(message.getMessageId()))
            messageRepository.delete(message.getMessageId());
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Iterable<? extends Message> iterable) {
        messageRepository.delete(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAll() {
        messageRepository.deleteAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void flush() {
        messageRepository.flush();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Message getOne(Integer integer) {
        return messageRepository.getOne(integer);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Message saveAndFlush(Message message) {
        return messageRepository.saveAndFlush(message);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<Message> save(Iterable<Message> iterable) {
        return messageRepository.save(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Message update(Message message) {
        return messageRepository.exists(message.getMessageId()) ? messageRepository.save(message) : null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Message> findMessagesByTicket(Ticket ticket, PageRequest pageRequest) {
        return messageRepository.findByTicketOrderByTimeDesc(ticket, pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Message> getAnswers(Integer parentId) {
        return messageRepository.findByParentIdOrderByTimeDesc(parentId);
    }

}

