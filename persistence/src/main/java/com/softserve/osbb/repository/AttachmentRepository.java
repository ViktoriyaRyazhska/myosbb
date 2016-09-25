package com.softserve.osbb.repository;

import com.softserve.osbb.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nataliia on 10.07.16.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    @Query("select a from Attachment a where lower(a.path) like lower(concat('%',:search,'%'))")
    List<Attachment> findAttachmentByPath(@Param("search") String path);
}
