package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.RegistrationService;
import com.softserve.osbb.service.SettingsService;
import com.softserve.osbb.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by nazar.dovhyy on 29.10.2016.
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);
    @Autowired
    private UserService userService;
    @Autowired
    private OsbbService osbbService;
    @Autowired
    private SettingsService settingsService;

    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED)
    @Override
    public User registrate(User user) {
        logger.info("registrating new user " + user);
        User registeredUser = userService.save(user);
        settingsService.save(new Settings(registeredUser));
        return registeredUser;
    }

    @Transactional(readOnly = false,
            isolation = Isolation.SERIALIZABLE,
            propagation = Propagation.REQUIRED)
    @Override
    public Osbb registrate(Osbb newOsbb) {
        logger.info("registering new osbb " + newOsbb);
        User creator = newOsbb.getCreator();
        creator = registrate(creator);
        newOsbb = osbbService.addOsbb(newOsbb);
        creator.setOsbb(newOsbb);
        userService.update(creator);
        return newOsbb;

    }
}
