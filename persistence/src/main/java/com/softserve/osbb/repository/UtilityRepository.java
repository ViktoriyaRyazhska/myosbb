package com.softserve.osbb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Utility;
/**
 * Created by YaroslavStefanyshyn on 02.13.17.
 */
@Repository
public interface UtilityRepository extends JpaRepository<Utility, Integer>{

}
