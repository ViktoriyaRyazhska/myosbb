package com.softserve.osbb.repository;

import com.softserve.osbb.model.Osbb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Roman on 06.07.2016.
 */
@Repository
public interface OsbbRepository extends JpaRepository<Osbb, Integer> {

    Osbb findByName(String name);

    List<Osbb> findByAvailable(Boolean available);

    List<Osbb> findByNameContainingIgnoreCase(String name);
}