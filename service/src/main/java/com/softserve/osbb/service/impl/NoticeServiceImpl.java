package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Notice;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.NoticeRepository;
import com.softserve.osbb.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Kris on 06.09.2016.
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    NoticeRepository noticeRepository;

    @Override
    public Notice save(Notice notice) {
        return noticeRepository.save(notice);
    }

    @Override
    public Notice findOne(Integer id) {
       return noticeRepository.findOne(id);
    }

    @Override
    public Notice findOne(String id) {
         try {
            return noticeRepository.findOne(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exists(Integer id) {
         return noticeRepository.exists(id);
    }

    @Override
    public void delete(Integer id) {
        if(exists(id))
          noticeRepository.delete(id);
    }

    @Override
    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    @Override
    public List<Notice> findNoticesOfUser(User user) {
        return noticeRepository.findByUser(user);
    }
}
