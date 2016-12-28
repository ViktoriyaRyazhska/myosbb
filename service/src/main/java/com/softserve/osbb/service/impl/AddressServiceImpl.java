package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.City;
import com.softserve.osbb.model.District;
import com.softserve.osbb.model.Folder;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Street;
import com.softserve.osbb.repository.RegionRepository;
import com.softserve.osbb.repository.CityRepository;
import com.softserve.osbb.repository.DistrictRepository;
import com.softserve.osbb.repository.StreetRepository;
import com.softserve.osbb.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YuriPushchalo on 16.11.2016
 */
@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private StreetRepository streetRepository;

	@Autowired
	private DistrictRepository districtRepository;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Region> getAllRegion() {
		return regionRepository.findAllByOrderByNameAsc();
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<City> getAllCitiesOfRegion(Integer regionId) {
		return cityRepository.findByRegionOrderByName(regionRepository.findById(regionId));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<Street> getAllStreetsOfCity(Integer cityId) {
		return streetRepository.findByCityOrderByName(cityRepository.findById(cityId));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public List<District> getAllDistrictsOfCity(Integer cityId) {
		return districtRepository.findByCityOrderByName(cityRepository.findById(cityId));
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public District getDistrictById(Integer id) {
		return districtRepository.findById(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Street getStreetById(Integer id) {
		return streetRepository.findById(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public City getCityById(Integer id) {
		return cityRepository.findById(id);
	}

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	@Override
	public Region getRegionById(Integer id) {
		return regionRepository.findById(id);
	}
 
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Region addRegion(Region region) {
        if(region == null) {
            throw new IllegalArgumentException("Region is null. Try to set correct data.");
        }
        return regionRepository.saveAndFlush(region);
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Region updateRegion(Region region) {
        if(regionRepository.exists(region.getId())) {
            return regionRepository.saveAndFlush(region);
        } else {
            throw new IllegalArgumentException("Region with id=" + region.getId()
                    + " doesn't exist. First try to add this region.");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteRegion(Integer id) {
    	if (getAllCitiesOfRegion(id).isEmpty()) {
        	regionRepository.delete(id);
            return (regionRepository.findById(id) == null);
    	} else {
            throw new IllegalArgumentException("Cities of region with id=" + id
            + " exist. First they should be deleted.");
    	}
    }

	
/*    City updateCity(Integer id);
    
    Street updateStreet(Integer id);
    
    District updateDistrict(Integer id);

    void deleteRegion(Integer id);

    void deleteCity(Integer id);

    void deleteStreet(Integer id);

    void deleteDistrict(Integer id);
*/
}
