package com.softserve.osbb.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.RoleRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.UserService;

/**
 * Created by cavayman on 11.07.2016.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository userRoleRepository;

    private Logger log;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User findOne(Integer integer) {
        return userRepository.findOne(integer);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User findOne(String id) {
        try {
            return userRepository.findOne(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            log.warn("UserService: Cant find user with id:" + id);
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean exists(Integer integer) {
        return userRepository.exists(integer);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAll(Sort sort) {
        return userRepository.findAll(sort);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> findAll(Iterable<Integer> iterable) {
        return userRepository.findAll(iterable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long count() {
        return userRepository.count();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Integer integer) {
        if (exists(integer)) {
            userRepository.delete(integer);
        } else {
            log.warn("Cant find user with id:" + integer);
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(User user) {
        if (exists(user.getUserId())) {
            userRepository.delete(user.getUserId());
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Iterable<? extends User> iterable) {
        userRepository.delete(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void flush() {
        userRepository.flush();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteInBatch(Iterable<User> iterable) {
        userRepository.deleteInBatch(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllInBatch() {
        userRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User getOne(Integer integer) {
        return userRepository.getOne(integer);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public User saveAndFlush(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.saveAndFlush(user);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<User> save(Iterable<User> iterable) {
        Iterator<User> ite = iterable.iterator();
        List<User> encodedUsers = new ArrayList<User>();
        while (ite.hasNext()) {
            User temp = ite.next();
            temp.setPassword(passwordEncoder.encode(temp.getPassword()));
            encodedUsers.add(temp);
        }
        return userRepository.save(encodedUsers);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public User findUserByEmail(String email) {
        try {
            List<User> temp = userRepository.findUserByEmail(email);
            return temp.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<User> getUsersByOsbb(Osbb osbb) {
        return userRepository.findByOsbb(osbb);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public User update(User user) {
        return userRepository.saveAndFlush(user);
    }
}
