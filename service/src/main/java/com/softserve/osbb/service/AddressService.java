package com.softserve.osbb.service;

import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.City;
import com.softserve.osbb.model.District;
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

    List<District> getAllDistrictsOfCity(Integer cityId);

    District getDistrictById(Integer id);

    Street getStreetById(Integer id);
    
    City getCityById(Integer id);

    Region getRegionById(Integer id);
    
    
    

}
