package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.SettingsRepository;
import com.softserve.osbb.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Kris on 17.09.2016.
 */
@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    SettingsRepository settingsRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Settings save(Settings settings) {
        return settingsRepository.save(settings);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Settings update(Settings settings) {
        return settingsRepository.exists(settings.getSettingsId()) ? settingsRepository.save(settings) : null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Settings findOne(Integer id) {
        return settingsRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Settings findByUser(User user) {
        return settingsRepository.findByUser(user);
    }

}
