package com.softserve.osbb.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.AddressDTO;
import com.softserve.osbb.model.City;
import com.softserve.osbb.model.District;
import com.softserve.osbb.model.Region;
import com.softserve.osbb.model.Street;
import com.softserve.osbb.service.AddressService;

/**
 * Created by YuriPushchalo on 16/11/2016.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/address")
public class AddressController {

    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @RequestMapping(value = "/region", method = RequestMethod.GET)
    public ResponseEntity<List<Region>> getAllRegion() {
        logger.info("Get all region: ");
        return new ResponseEntity<>(addressService.getAllRegion(), HttpStatus.OK);
    }

    @RequestMapping(value = "/city/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<City>> getAllCitiesOfRegion(@PathVariable("id") Integer id) {
        logger.info("Get all cities of region: ");
        HttpStatus status = HttpStatus.OK;
        List<City> cities = new ArrayList<>();
        if (addressService.getRegionById(id) == null) {
        	status = HttpStatus.NOT_FOUND;
        } else {
        	cities.addAll(addressService.getAllCitiesOfRegion(id));
        }
        return new ResponseEntity<>(cities, status);
    }

    @RequestMapping(value = "/street/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Street>> getAllStreetsOfCity(@PathVariable("id") Integer id) {
        logger.info("Get all streets of city: ");
        HttpStatus status = HttpStatus.OK;
        List<Street> streets = new ArrayList<>();
        if (addressService.getCityById(id) == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            streets.addAll(addressService.getAllStreetsOfCity(id));
        }
        return new ResponseEntity<>(streets, status);
    }

    @RequestMapping(value = "/street/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<Street> getStreetById(@PathVariable("id") Integer id) {
        logger.info("Get street by Id: ");
        Street street = addressService.getStreetById(id);
        HttpStatus status = HttpStatus.OK;
        if (street == null) {
        	street = new Street();
        	status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(street, status);
    }

    @RequestMapping(value = "/district/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<District>> getAllDistrictsOfCity(@PathVariable("id") Integer id) {
        logger.info("Get all districts of city: ");
        HttpStatus status = HttpStatus.OK;
        List<District> districts = new ArrayList<>();
        if (addressService.getCityById(id) == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            districts.addAll(addressService.getAllDistrictsOfCity(id));
        }
        return new ResponseEntity<>(districts, status);
    }

    @RequestMapping(value = "/district/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<District> getDistrictById(@PathVariable("id") Integer id) {
        logger.info("Get district by Id: ");
        District district = addressService.getDistrictById(id);
        HttpStatus status = HttpStatus.OK;
        if (district == null) {
        	district = new District();
        	status = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity<>(district, status);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/region", method = RequestMethod.POST)
    public AddressDTO addRegion(@RequestBody AddressDTO added) {        
        Region region = addressService.addRegion(new Region(added.getId(), added.getName())); 
        return new AddressDTO(region.getId(), region.getName(), null);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/region", method = RequestMethod.PUT)
    public AddressDTO updateRegion(@RequestBody AddressDTO updated) {        
        Region region = addressService.updateRegion(new Region(updated.getId(), updated.getName())); 
        return new AddressDTO(region.getId(), region.getName(), null);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/region/{id}", method = RequestMethod.DELETE)
    public boolean deleteRegion(@PathVariable Integer id) {        
        return addressService.deleteRegion(id);
    }    
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/city", method = RequestMethod.POST)
    public AddressDTO addCity(@RequestBody AddressDTO added) { 
        City city = addressService.addCity(new City(added.getId(), added.getName(), 
        		      addressService.getRegionById(added.getOwnerId()))); 
        return new AddressDTO(city.getId(), city.getName(), city.getRegion().getId());
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/city", method = RequestMethod.PUT)
    public AddressDTO updateCity(@RequestBody AddressDTO updated) {        
        City city = addressService.updateCity(new City(updated.getId(), updated.getName(),
        				addressService.getRegionById(updated.getOwnerId()))); 
        return new AddressDTO(city.getId(), city.getName(), null);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/city/{id}", method = RequestMethod.DELETE)
    public boolean deleteCity(@PathVariable Integer id) {        
        return addressService.deleteCity(id);
    }    
    
}
