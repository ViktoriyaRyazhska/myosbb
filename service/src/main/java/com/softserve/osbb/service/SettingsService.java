package com.softserve.osbb.service;

import com.softserve.osbb.model.Message;
import com.softserve.osbb.model.Settings;
import com.softserve.osbb.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by Kris on 17.09.2016.
 */
@Service
public interface SettingsService {
    Settings save(Settings settings);

    Settings update(Settings settings);

    Settings findOne(Integer id);

    Settings findByUser(User user);



}
