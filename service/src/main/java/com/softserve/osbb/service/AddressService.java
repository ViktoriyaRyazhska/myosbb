package com.softserve.osbb.service;

import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.City;
import com.softserve.osbb.model.Street;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by YuriPushchalo on 16.11.2016
 */
@Service
public interface AddressService {

    List<Region> getAllRegion();

    List<City> getAllCitiesOfRegion(Integer regionId);

    List<Street> getAllStreetsOfCity(Integer cityId);

    Street getStreetById(Integer id);
    
}
