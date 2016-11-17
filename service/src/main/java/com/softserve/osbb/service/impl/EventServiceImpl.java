package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Event;
import com.softserve.osbb.model.enums.EventStatus;
import com.softserve.osbb.repository.EventRepository;
import com.softserve.osbb.service.EventService;
import com.softserve.osbb.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by nataliia on 10.07.16.
 */
@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<Event> saveEvents(List<Event> list) {
        return eventRepository.save(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> getEvents(List<Event> list) {
        return eventRepository.save(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Event getEventById(Integer id) {
        return eventRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> getEventsByIds(List<Integer> ids) {
        return eventRepository.findAll(ids);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Event updateEvent(Integer id, Event event) {
        return eventRepository.exists(id) ? eventRepository.save(event) : null;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteEvent(Event event) {
        eventRepository.delete(event);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteEventById(Integer id) {
        eventRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteEvents(List<Event> list) {
        eventRepository.delete(list);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllEvents() {
        eventRepository.deleteAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long countEvents() {
        return eventRepository.count();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsEvent(Integer id) {
        return eventRepository.exists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Event> getAllEvents(Integer pageNumber, String sortBy, Boolean order) {
        PageRequest pageRequest = new PageRequest(pageNumber - 1, Constants.DEF_ROWS,
                getSortingOrder(order), sortBy == null ? "startTime" : sortBy);
        return eventRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> findByAuthor(String user) {
        return eventRepository.findByAuthorEmail(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> findByStatus(EventStatus status) {
        return eventRepository.findAll()
                .stream()
                .filter(event -> status.equals(event.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> findEventsByTitleOrAuthorOrDescription(String search) {
        return eventRepository.findByTitleOrAuthorOrDescription(search);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Event> findByInterval(Timestamp start, Timestamp end) {
        return eventRepository.findBetweenStartTimeAndEndTime(start, end);
    }

    private Sort.Direction getSortingOrder(Boolean order) {
        if (order == null) {
            return Sort.Direction.DESC;
        }
        return order ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
