/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.softserve.osbb.dto.UserDTO;
import com.softserve.osbb.dto.mappers.UserDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.BillService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.util.paging.PageDataObject;

/**
 * Created by Oleg on 13.07.2016.
 * @version 1.1
 * @since 22.11.2016
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/apartment")
public class ApartmentController {

    private static final Logger logger = LoggerFactory.getLogger(ApartmentController.class);
    
    @Autowired
    ApartmentService apartmentService;

    @Autowired
    UserService userService;
    
    @Autowired
    BillService billService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Apartment>>> getAllApartment() {
        logger.info("Getting all apartments.");        
        return new ResponseEntity<>(getResources(apartmentService.findAll()), HttpStatus.OK);
    }

    /*
     * Processes list by adding self-link
     */
    private List<Resource<Apartment>> getResources(List<Apartment> apartments) {
        List<Resource<Apartment>> resources = new ArrayList<>();
        
        for (Apartment a: apartments) {
            resources.add(addResourceLinkToApartment(a));
        }
        
        return resources;
    }
    
    /*
     * Adds link to passed resource
     */
    private Resource<Apartment> addResourceLinkToApartment(Apartment apartment) {
        
        // apartment may by null if findById din not found object in database
        if (apartment == null) {
            return null;
        }
        
        Resource<Apartment> resource = new Resource<>(apartment);
        resource.add(linkTo(methodOn(ApartmentController.class)
                .findApartmentById(apartment.getApartmentId()))
                .withSelfRel());
        
        return resource;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Apartment>>> getAllApartment(
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder,
            @RequestParam(value = "number", required = false) Integer number,
            @RequestParam(value = "osbbId", required = false) Integer osbbId,
            @RequestParam(value = "role", required = false) String role) {
        
        logger.info("Getting all apartments by page number: " + pageNumber);
        Page<Apartment> apartmentByPage = null;
   
        if (role.equals("ROLE_ADMIN")) {
            apartmentByPage = apartmentService.getByPageNumber(pageNumber, sortedBy, ascOrder, number);
        } else {
            apartmentByPage = apartmentService.getByPageNumber(pageNumber, sortedBy, ascOrder, number, osbbId);
        }
        
        return new ResponseEntity<>(preparePageDataObjcet(apartmentByPage), HttpStatus.OK);
    }

    private PageDataObject<Resource<Apartment>> preparePageDataObjcet(
            Page<Apartment> apartmentByPage) {
        int currentPage = apartmentByPage.getNumber() + 1;
        int begin = Math.max(1, currentPage - 5);
        int totalPages = apartmentByPage.getTotalPages();
        int end = Math.min(currentPage + 5, totalPages);

        List<Resource<Apartment>> resourceList = new ArrayList<>();
        apartmentByPage.forEach((apartment) -> resourceList.add(getLink(toResource(apartment))));        

        PageDataObject<Resource<Apartment>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(String.valueOf(currentPage));
        pageDataObject.setBeginPage(String.valueOf(begin));
        pageDataObject.setEndPage(String.valueOf(end));
        pageDataObject.setTotalPages(String.valueOf(totalPages));
        return pageDataObject;
    }    

    private Resource<Apartment> getLink(Resource<Apartment> apartmentResource) {
        apartmentResource.add(linkTo(methodOn(ApartmentController.class)
                .findApartmentById(apartmentResource.getContent().getApartmentId()))
                .withSelfRel());
        
        return apartmentResource;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Resource<Apartment>> addUserToApartment(@PathVariable("id") Integer id, @RequestBody User user) {
        HttpStatus status = HttpStatus.OK;
        
        // may return null if not present in database, so need to check for null
        Apartment apartment = apartmentService.findById(id);
        
        if (apartment != null) {        
            if (user != null && user.isOwner() && apartment.getOwner() == null) {
                apartment.setOwner(user.getUserId());
                user.setApartment(apartment);
                
                if (userService.saveAndFlush(user) == null) {
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                }
            } else {
                logger.info("Owner already exist");
                status = HttpStatus.BAD_REQUEST;
            }            
        } else {
            status = HttpStatus.BAD_REQUEST;            
        }

        return new ResponseEntity<>(status);
    }

    @RequestMapping(value = "/users{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<UserDTO>>> getUsersInApartment(@PathVariable("id") Integer id) {
        List<UserDTO> users = UserDTOMapper.mapUserEntityToDTO(apartmentService.findById(id).getUsers());
        
        final List<Resource<UserDTO>> resources = new ArrayList<>();
        for (UserDTO user : users) {
            resources.add(addResourceLinkToUserDTO(user));
        }
        
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
    
    /*
     * Adds link to UserTDO that will be sent to view (after wrapping into Response object)
     */
    private Resource<UserDTO> addResourceLinkToUserDTO(UserDTO user) {
        if (user == null) {
            return null;
        }
        
        Resource<UserDTO> userResource = new Resource<>(user);
        userResource.add(linkTo(methodOn(UserController.class)
                .getUser(user.getUserId().toString()))
                .withSelfRel());
        
        return userResource;
    }

    @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<UserDTO>> getOwnerInApartment(@PathVariable("id") Integer id) {
        HttpStatus status = HttpStatus.OK;
        UserDTO user = null;
        
        logger.info("Getting owner of apartment  " + id);
        Apartment apartment = apartmentService.findById(id);
        
        if (apartment == null || apartment.getOwner() == null) {
            status = HttpStatus.NOT_FOUND;
        } else {
            user = UserDTOMapper.mapUserEntityToDTO(userService.findOne(apartment.getOwner()));
        }
        
        return new ResponseEntity<>(addResourceLinkToUserDTO(user), status);
    }

    @RequestMapping(value = "/{id}/bills", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Bill>>> getAllBils(@PathVariable("id") Integer Id) {
        List<Bill> bills = billService.getAllBillsByApartmentWithCurrentMonth(Id);        
        List<Resource<Bill>> resources = new ArrayList<>();
        
        for (Bill bill : bills) {
            resources.add(addResourceLinkToBill(bill));
        }
        
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }
    
    /*
     * Adds link to bill that will be sent to view (after wrapping into Response object)
     */
    private Resource<Bill> addResourceLinkToBill(Bill bill) {
        
        // find billById may return null if not present in database, ned to check
        if (bill == null) {
            return null;
        }
        
        Resource<Bill> billResource = new Resource<>(bill);
        billResource.add(linkTo(methodOn(BillController.class)
                .findOneBill(bill.getBillId()))
                .withSelfRel());
        
        return billResource;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Apartment>> addApartment(@RequestBody Apartment apartment) {
        logger.info("Saving apartment " + apartment);
        Apartment saved = apartmentService.save(apartment);
        
        return buildResponseEntity(saved);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Apartment>> findApartmentById(@PathVariable("id") Integer id) {        
        logger.info("Getting apartment with id: " + id);
        Apartment apartment = apartmentService.findById(id);
       
        return buildResponseEntity(apartment);
    }    

    private ResponseEntity<Resource<Apartment>> buildResponseEntity(Apartment apartment) {
        HttpStatus status = HttpStatus.OK;
        ResponseEntity<Resource<Apartment>> response = null;
        
        if (apartment == null) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            response = new ResponseEntity<>(status);
        } else {
            response = new ResponseEntity<>(addResourceLinkToApartment(apartment), status);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Apartment>> deleteAppartmentById(@PathVariable("id") Integer id) {
        HttpStatus status = HttpStatus.OK;        
        logger.info("Deleting apartment wit id " + id);
        
        try {
            apartmentService.deleteById(id);
        } catch (IllegalArgumentException e) {
            logger.warn("Could not delete apartment with id " + id);
            status = HttpStatus.BAD_REQUEST;
        } 
        
        return new ResponseEntity<>(status);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Apartment>> updateApartment(@RequestBody Apartment apartment) {
        apartment.setUsers(apartmentService.findById(apartment.getApartmentId()).getUsers());
        apartment.setBills(apartmentService.findById(apartment.getApartmentId()).getBills());
        Apartment updated = apartmentService.update(apartment);

        return new ResponseEntity<>(addResourceLinkToApartment(updated), 
                updated == null ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.OK);
    }

}
