package com.softserve.osbb.repository;

import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by Kris on 15.09.2016.
 */
@Repository
public interface SettingsRepository extends JpaRepository<Settings, Integer>, JpaSpecificationExecutor<Settings> {

    Settings findByUser(User user);

}
