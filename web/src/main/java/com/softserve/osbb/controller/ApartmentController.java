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
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/apartment")
public class ApartmentController {

    @Autowired
    ApartmentService apartmentService;

    @Autowired
    UserService userService;
    
    @Autowired
    BillService billService;

    private static Logger logger = LoggerFactory.getLogger(ApartmentController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Apartment>>> getAllApartment() {
        logger.info("Getting all apartments.");
        List<Apartment> apartmentList = apartmentService.findAllApartment();
        List<Resource<Apartment>> resourceApartmentList = new ArrayList<>();
        
        for (Apartment a : apartmentList) {
            resourceApartmentList.add(addResourceLinkToApartment(a));

        }
        
        return new ResponseEntity<>(resourceApartmentList, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Apartment>>> getAllApartment(
            @RequestParam(value = "pageNumber", required = true) Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder,
            @RequestParam(value = "number", required = false) Integer number,
            @RequestParam(value = "osbbId", required = false) Integer osbbId,
            @RequestParam(value = "role", required = false) String role) {
        
        logger.info("get all apartments by page number: " + pageNumber);

        Page<Apartment> apartmentByPage = null;
        
        try {
            if (role.equals("ROLE_ADMIN")) {
                apartmentByPage = apartmentService.getAllApartmentsToAdmin(pageNumber, sortedBy, ascOrder, number);
            } else {
                apartmentByPage = apartmentService.getAllApartment(pageNumber, sortedBy, ascOrder, number, osbbId);
            }
        } catch (Exception e) {
            logger.warn("Apartment wasn't found");
        }
        
        int currentPage = apartmentByPage.getNumber() + 1;
        int begin = Math.max(1, currentPage - 5);
        int totalPages = apartmentByPage.getTotalPages();
        int end = Math.min(currentPage + 5, totalPages);

        List<Resource<Apartment>> resourceList = new ArrayList<>();
        apartmentByPage.forEach((apartment) -> resourceList.add(getLink(toResource(apartment))));

        PageDataObject<Resource<Apartment>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(Integer.valueOf(currentPage).toString());
        pageDataObject.setBeginPage(Integer.valueOf(begin).toString());
        pageDataObject.setEndPage(Integer.valueOf(end).toString());
        pageDataObject.setTotalPages(Integer.valueOf(totalPages).toString());

        return new ResponseEntity<>(pageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public ResponseEntity<Resource<Apartment>> addUserToApartment(
            @PathVariable("id") Integer id, @RequestBody User user) {
        Apartment newApartment = apartmentService.findOneApartmentByID(id);
        user = userService.findOne(user.getUserId());
        
        if ((user.isOwner()) && (newApartment.getOwner() == null)) {
            newApartment.setOwner(user.getUserId());
        } else {
            logger.info("Owner already exist");
        }
        
        user.setApartment(newApartment);
        userService.saveAndFlush(user);

        logger.info("size of  list " + newApartment.getUsers().size());

        return new ResponseEntity<>(addResourceLinkToApartment(newApartment), HttpStatus.OK);
    }

    @RequestMapping(value = "/users{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<UserDTO>>> getUsersInApartment(@PathVariable("id") Integer id) {
        List<UserDTO> userList = UserDTOMapper.mapUserEntityToDTO(apartmentService.findOneApartmentByID(id).getUsers());
        logger.info("Getting all users.");

        final List<Resource<UserDTO>> resourceUserList = new ArrayList<>();
        for (UserDTO u : userList) {
            resourceUserList.add(addResourceLinkToUserDTO(u));
        }
        
        return new ResponseEntity<>(resourceUserList, HttpStatus.OK);
    }

    @RequestMapping(value = "/owner/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<UserDTO>> getOwnerInApartment(@PathVariable("id") Integer id) {
        logger.info("getting owner of apartment  " + id);
        Apartment apartment = apartmentService.findOneApartmentByID(id);
        UserDTO user;
        Resource<UserDTO> userDTOResource;
        
        if (apartment.getOwner() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
            
        user = UserDTOMapper.mapUserEntityToDTO(userService.findOne(apartment.getOwner()));
        userDTOResource = addResourceLinkToUserDTO(user);    

        return new ResponseEntity<>(userDTOResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/bills", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Bill>>> getAllBils(@PathVariable("id") Integer Id) {
        List<Bill> bills = billService.getAllBillsByApartmentWithCurrentMonth(Id);        
        List<Resource<Bill>> resourceBillsList = new ArrayList<>();
        logger.info("size= " + bills.size());
        
        for (Bill bill : bills) {
            resourceBillsList.add(addResourceLinkToBill(bill));
        }
        
        return new ResponseEntity<>(resourceBillsList, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Apartment>> addApartment(@RequestBody Apartment apartment) {
        logger.info("saving apartment" + apartment);
        
        try {
            apartmentService.saveApartment(apartment);
        } catch (Exception e) {
            logger.warn("Apartment was'n saved");
        }
        
        return new ResponseEntity<>(addResourceLinkToApartment(apartment), HttpStatus.OK);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Apartment>> findApartmentById(@PathVariable("id") Integer id) {
        logger.info("getting apartment by id:" + id);
        Apartment apartment = null;
        
        try {
            apartment = apartmentService.findOneApartmentByID(id);
        } catch (Exception e) {
            logger.warn("Aparatment not found");
        }
        
        return new ResponseEntity<>(addResourceLinkToApartment(apartment), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Apartment>> deleteAppartmentById(@PathVariable("id") Integer id) {
        logger.info("deleting ");
        Apartment apartment = apartmentService.findOneApartmentByID(id);
        
        try {
            apartmentService.deleteApartmentByID(id);
        } catch (Exception e) {
            logger.warn("Apartment can't be deleted");
        }
        
        return new ResponseEntity<>(addResourceLinkToApartment(apartment), HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Apartment>> updateApartment(@RequestBody Apartment apartment) {
        logger.info("updating ");
        apartment.setUsers(apartmentService.findOneApartmentByID(apartment.getApartmentId()).getUsers());
        apartment.setBills(apartmentService.findOneApartmentByID(apartment.getApartmentId()).getBills());
        Apartment uApartment = apartmentService.updateApartment(apartment);

        return new ResponseEntity<>(addResourceLinkToApartment(uApartment), HttpStatus.OK);
    }

    private Resource<Apartment> addResourceLinkToApartment(Apartment apartment) {
        if (apartment == null) return null;
        
        Resource<Apartment> apartmentResource = new Resource<>(apartment);
        apartmentResource.add(linkTo(methodOn(ApartmentController.class)
                .findApartmentById(apartment.getApartmentId()))
                .withSelfRel());
        
        return apartmentResource;
    }

    private Resource<Bill> addResourceLinkToBill(Bill bill) {
        if (bill == null) return null;
        
        Resource<Bill> billResource = new Resource<>(bill);
        billResource.add(linkTo(methodOn(BillController.class)
                .findOneBill(bill.getBillId()))
                .withSelfRel());
        
        return billResource;
    }

    private Resource<UserDTO> addResourceLinkToUserDTO(UserDTO user) {
        if (user == null) return null;
        
        Resource<UserDTO> userResource = new Resource<>(user);
        userResource.add(linkTo(methodOn(UserController.class)
                .getUser(user.getUserId().toString()))
                .withSelfRel());
        
        return userResource;
    }

    private Resource<Apartment> getLink(Resource<Apartment> apartmentResource) {
        apartmentResource.add(linkTo(methodOn(ApartmentController.class)
                .findApartmentById(apartmentResource.getContent().getApartmentId()))
                .withSelfRel());
        
        return apartmentResource;
    }

}
