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

    Region getRegionById(Integer id);
    
    Region addRegion(Region region);

    Region updateRegion(Region region);

    boolean deleteRegion(Integer id);

    List<City> getAllCitiesOfRegion(Integer regionId);

    City getCityById(Integer id);
    
    City addCity(City city);

    City updateCity(City city);

    boolean deleteCity(Integer id);

    List<Street> getAllStreetsOfCity(Integer cityId);

    Street getStreetById(Integer id);

    Street addStreet(Street street);

    Street updateStreet(Street street);

    boolean deleteStreet(Integer id);

    List<District> getAllDistrictsOfCity(Integer cityId);

    District getDistrictById(Integer id);

    
/*        Street updateStreet(Street street);
    
    District updateDistrict(District district);


    void deleteCity(Integer id);

    void deleteStreet(Integer id);

    void deleteDistrict(Integer id);
*/
}
