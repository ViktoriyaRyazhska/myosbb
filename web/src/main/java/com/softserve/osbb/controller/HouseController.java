/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.HouseDTO;
import com.softserve.osbb.dto.PageParams;
import com.softserve.osbb.dto.mappers.HouseDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.HouseService;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.util.paging.PageDataObject;
import com.softserve.osbb.util.paging.PageDataUtil;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import com.softserve.osbb.util.paging.impl.HousePageDataObject;
import com.softserve.osbb.util.resources.ResourceLinkCreator;
import com.softserve.osbb.util.resources.impl.ApartmentResourceList;
import com.softserve.osbb.util.resources.impl.EntityResourceList;
import com.softserve.osbb.util.resources.impl.HouseResourceList;
import com.softserve.osbb.utils.Constants;

/**
 * Created by nazar.dovhyy on 19.07.2016.
 */
@RestController
@CrossOrigin()
@RequestMapping("/restful/house")
public class HouseController {

    private static final Logger logger = LoggerFactory.getLogger(HouseController.class);
    
    @Autowired
    HouseService houseService;
    
    @Autowired
    ApartmentService apartmentService;
    
    @Autowired
    OsbbService osbbService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<EntityResourceList<HouseDTO>> getAllHousesList() {
        logger.info("listing all houses");
        List<House> housesList = new ArrayList<House>();
        housesList.addAll(houseService.findAll());
        
        EntityResourceList<HouseDTO> houseEntityResourceList = new HouseResourceList();
        housesList.forEach((house) -> {
            HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
            houseEntityResourceList.add(toResource(houseDTO));
        });
        return new ResponseEntity<>(houseEntityResourceList, HttpStatus.OK);
    }

    @RequestMapping(value = "/all/{osbbId}", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<HouseDTO>>> getAllHousesByOsbbList(
            @PathVariable(value = "osbbId") Integer osbbId) {
        logger.info("get all houses by OsbbId: " + osbbId);
        List<House> houses = houseService.getAllHousesByOsbb(osbbId);
        EntityResourceList<HouseDTO> houseDTOEntityResourceList = new HouseResourceList();
        houses.forEach(house -> {
                    HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
                    logger.info("houseDTO created " + houseDTO.toString());
                    houseDTOEntityResourceList.add(toResource(houseDTO));
                }
        );

        return new ResponseEntity<>(houseDTOEntityResourceList, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ResponseEntity<PageDataObject<Resource<HouseDTO>>> listAllHousesByPage(
            @RequestBody PageParams pageParams) {
        logger.info("get all houses by page number: " + pageParams.getPageNumber());
        final PageRequest pageRequest = PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
                .addSortedBy(pageParams.getSortedBy(), "street")
                .addOrderType(pageParams.getOrderType())
                .addRows(pageParams.getRowNum())
                .toPageRequest();
        Page<House> housesByPage = houseService.getAllHouses(pageRequest);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(housesByPage);
        EntityResourceList<HouseDTO> houseDTOEntityResourceList = new HouseResourceList();
        
        housesByPage.forEach(house -> {
                    HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
                    logger.info("houseDTO created " + houseDTO.toString());
                    houseDTOEntityResourceList.add(toResource(houseDTO));
                }
        );
        
        PageDataObject<Resource<HouseDTO>> houseDTOPageDataObject = PageDataUtil
                .providePageData(HousePageDataObject.class, pageSelector, houseDTOEntityResourceList);

        return new ResponseEntity<>(houseDTOPageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/current", method = RequestMethod.POST)
    public ResponseEntity<PageDataObject<Resource<HouseDTO>>> listAllHousesByOsbbAndByPage(
            @RequestBody PageParams pageParams,
            @AuthenticationPrincipal Principal user) {
        User currentUser = userService.findUserByEmail(user.getName());
        final Integer osbbId = currentUser.getOsbb().getOsbbId();
        logger.info("get all houses by osbb: " + osbbId + ". by page number: " + pageParams.getPageNumber());
        final PageRequest pageRequest = PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
                .addSortedBy(pageParams.getSortedBy(), "street")
                .addOrderType(pageParams.getOrderType())
                .addRows(pageParams.getRowNum())
                .toPageRequest();
        Page<House> housesByPage = houseService.getAllHousesByOsbb(osbbService.getOsbb(osbbId) , pageRequest);
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(housesByPage);
        EntityResourceList<HouseDTO> houseDTOEntityResourceList = new HouseResourceList();
        
        housesByPage.forEach(house -> {
                    HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
                    logger.info("houseDTO created " + houseDTO.toString());
                    houseDTOEntityResourceList.add(toResource(houseDTO));
                }
        );
        
        PageDataObject<Resource<HouseDTO>> houseDTOPageDataObject = PageDataUtil.providePageData(HousePageDataObject.class, pageSelector, houseDTOEntityResourceList);
        return new ResponseEntity<>(houseDTOPageDataObject, HttpStatus.OK);
    }
    @RequestMapping(value = "/{id}/apartments", method = RequestMethod.GET)
    public ResponseEntity<EntityResourceList<Apartment>> getAllApartmentsByHouseId(@PathVariable("id") Integer houseId) {
        logger.info("get all apartments by house id: " + houseId);
        final House house;
        final EntityResourceList<Apartment> resourceApartmentList = new ApartmentResourceList();
        
        try {
            house = houseService.findHouseById(houseId);
            List<Apartment> apartmentList = (List<Apartment>) house.getApartments();
            
            apartmentList.forEach((apartment) -> {
                logger.info("apartment number: " + apartment.getNumber());
                Resource<Apartment> apartmentLink = resourceApartmentList
                        .createLink(toResource(apartment));
                resourceApartmentList.add(apartmentLink);
            });
        } catch (Exception e) {
            logger.error("error finding house by id: ", houseId);
            throw new HouseNotFoundException(e);
        }

        return new ResponseEntity<>(resourceApartmentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<EntityResourceList<HouseDTO>> findAllHousesByName(
            @RequestParam(value = "searchParam", required = true) String searchParam
    ) {
        logger.info("searching by searchParam ", searchParam);
        final EntityResourceList<HouseDTO> houseDTOEntityResourceList = new HouseResourceList();
        List<House> houseList = houseService.getAllHousesBySearchParameter(searchParam);
        
        houseList.forEach((house) -> {
            houseDTOEntityResourceList.add(toResource(HouseDTOMapper.mapHouseEntityToDTO(house)));
        });

        return new ResponseEntity<>(houseDTOEntityResourceList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<HouseDTO>> createHouse(@RequestBody HouseDTO houseDTO) {
        logger.info("adding new house to database");
        House house = HouseDTOMapper.mapHouseDTOToHouse(houseDTO);
        house = houseService.addHouse(house);
        
        if (house == null) {
            logger.error("house was not added to the database");
            throw new HouseNotSavedException();
        }
        
        house.setApartments(createRandomApartments(houseDTO.getApartmentCount(), house));
        houseService.updateHouse(house.getHouseId(), house);
        logger.info("house updated with random apartments created");
        ResourceLinkCreator<HouseDTO> resourceLinkCreator = new HouseResourceList();
        houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
        Resource<HouseDTO> resourceHouseDTO = resourceLinkCreator.createLink(toResource(houseDTO));
        return new ResponseEntity<>(resourceHouseDTO, HttpStatus.OK);
    }

    private Set<Apartment> createRandomApartments(Integer total, House house) {
        int apartmentCount = total == null ? Constants.TOTAL_APARTMENT_NUMBER : total;
        Set<Apartment> apartmentList = new HashSet<>();
        
        for (int i = 0; i < apartmentCount; i++) {
            Apartment a = new Apartment();
            a.setNumber(i + 1);
            a.setHouse(house);
            apartmentList.add(a);
        }
        
        return apartmentList;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Resource<Apartment>> addApartmentToHouse(@PathVariable("id") Integer id, @RequestBody Apartment apartment) {
        House house = houseService.findHouseById(id);
        apartment.setHouse(house);
        apartmentService.save(apartment);

        ResourceLinkCreator<Apartment> apartmentResourceLinkCreator = new ApartmentResourceList();
        Resource<Apartment> apartmentResource = apartmentResourceLinkCreator.createLink(toResource(apartment));
        return new ResponseEntity<>(apartmentResource, HttpStatus.OK);
    }


    @RequestMapping(value="/{houseId}/{apartmentNumber}",method=RequestMethod.GET)
    public boolean isApartmentNumberValid (@PathVariable("houseId") Integer houseId,@PathVariable("apartmentNumber") Integer apartmentNumber){
        boolean valid=true;
        List<Apartment> apartments = houseService.findAllApartmentsByHouseId(houseId);
        
        for (Apartment apartment:apartments){
            if (apartment.getNumber()==apartmentNumber){
                valid=false;
                break;
            }
        }
        
        return valid;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<HouseDTO>> getHouseById(@PathVariable("id") Integer houseId) {
        House house;
        HouseDTO houseDTO;
        Resource<HouseDTO> houseResource = null;
        
        try {
            house = houseService.findHouseById(houseId);
            houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
            ResourceLinkCreator<HouseDTO> houseResourceLinkCreator = new HouseResourceList();
            houseResource = houseResourceLinkCreator.createLink(toResource(houseDTO));
        } catch (Exception e) {
            logger.error("error finding house by id: ", houseId);
            throw new HouseNotFoundException(e);
        }
        
        return new ResponseEntity<>(houseResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{houseId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteHouseById(@PathVariable("houseId") Integer houseId) {
        logger.info("deleting house by id: " + houseId);
        final boolean isDeleted = houseService.deleteHouseById(houseId);
        
        if (!isDeleted) {
            logger.warn("no house was found by id: " + houseId);
            throw new HouseNotFoundException();
        }
        
        logger.info("successfully deleted house with id: " + houseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "House not found")
    private class HouseNotFoundException extends RuntimeException {

        public HouseNotFoundException() {
        }

        public HouseNotFoundException(Exception e) {
            super(e);
        }
    }

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "House not saved")
    private class HouseNotSavedException extends RuntimeException {

        public HouseNotSavedException() {
        }
    }
}
