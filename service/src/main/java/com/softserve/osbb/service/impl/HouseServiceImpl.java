package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.repository.HouseRepository;
import com.softserve.osbb.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
@Service
public class HouseServiceImpl implements HouseService {

    private static final List<House> EMPTY_HOUSE_LIST = new ArrayList<>(0);
    private static final List<Apartment> EMPTY_APPARTMENT_LIST = new ArrayList<>(0);

    @Autowired
    private HouseRepository houseRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public House addHouse(House house) {
        return house == null ? House.NULL : addHouseIfNotNull(house);
    }

    private House addHouseIfNotNull(House house) {
        house = houseRepository.save(house);
        return house;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public House updateHouse(Integer houseId, House house) {
        return house == null ? House.NULL : updateHouseIfExists(house);
    }

    private House updateHouseIfExists(House house) {
        final boolean houseExists = houseRepository.equals(house);
        if (!houseExists) {
            return House.NULL;
        }
        return house;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public House findHouseById(Integer houseId) {
        return houseRepository.exists(houseId) ? houseRepository.findOne(houseId) : House.NULL;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<House> getAllHousesBySearchParameter(String searchTerm) {
        return (searchTerm == null || searchTerm.isEmpty()) ?
                EMPTY_HOUSE_LIST : houseRepository.getAlReportsBySearchParameter(searchTerm);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<House> findAllByCity(String city) {
        return (city == null || city.isEmpty()) ?
                EMPTY_HOUSE_LIST : houseRepository.findByCity(city);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<House> findAllByStreet(String street) {
        return (street == null || street.isEmpty()) ?
                EMPTY_HOUSE_LIST : houseRepository.findByStreet(street);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<House> findAll() {
        return houseRepository.findAll();
    }


    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Apartment> findAllApartmentsByHouseId(Integer houseId) {
        House house = findHouseById(houseId);
        return house != null ? (List<Apartment>) house.getApartments() : EMPTY_APPARTMENT_LIST;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public boolean deleteHouseById(Integer id) {
        final boolean houseExists = houseRepository.exists(id);
        if (!houseExists) {
            return false;
        }
        houseRepository.delete(id);
        return true;

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllHouses() {
        houseRepository.deleteAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<House> getAllHouses(Pageable pageable) {
        return houseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<House> getAllHousesByOsbb(Integer osbbId) {
        return houseRepository.findByOsbbId(osbbId);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<House> getAllHousesByOsbb(Osbb osbb, Pageable pageable) {
        return houseRepository.findByOsbb(osbb, pageable);
    }
}
