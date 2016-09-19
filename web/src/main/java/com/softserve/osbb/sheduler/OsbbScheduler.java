package com.softserve.osbb.sheduler;

import com.softserve.osbb.controller.OsbbController;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.service.OsbbService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Roman on 19.09.2016.
 */
@Component
public class OsbbScheduler {
    private static Logger logger = LoggerFactory.getLogger(OsbbController.class);

    @Autowired
    OsbbService osbbService;

    @Scheduled(fixedRate = 86400000)
    public void deleteDisabledOsbb() {
        logger.info("Osbb scheduler: try to delete disabled osbb.");
        List<Osbb> osbbList = osbbService.findByAvailable(false);
        System.out.println("*******ARR: " + osbbList);
        for(Osbb osbb: osbbList) {
            logger.info("Osbb scheduler: delete disabled osbb : " + osbb);
            osbbService.deleteOsbb(osbb);
        }
    }
}
