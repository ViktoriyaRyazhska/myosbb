package com.softserve.osbb.repository;

import com.softserve.osbb.model.Notice;
import com.softserve.osbb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kris on 06.09.2016.
 */
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Integer> {

    List<Notice> findByUser(User user);
}
