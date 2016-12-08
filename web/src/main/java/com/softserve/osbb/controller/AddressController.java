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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.model.City;
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

}
