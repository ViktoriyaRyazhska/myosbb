/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.softserve.osbb.dto.AppartmentUserDTO;
import com.softserve.osbb.dto.HouseDTO;
import com.softserve.osbb.dto.PageParams;
import com.softserve.osbb.dto.UserRegitrationByAdminDTO;
import com.softserve.osbb.dto.mappers.HouseDTOMapper;
import com.softserve.osbb.dto.mappers.UserRegistrationByAdminDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.House;
import com.softserve.osbb.model.User;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.GoogleDriveService;
import com.softserve.osbb.service.AppartmentUserRegistrationService;
import com.softserve.osbb.service.HouseService;
import com.softserve.osbb.service.OsbbService;
import com.softserve.osbb.service.RegistrationService;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.exceptions.InvalidEmailException;
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
	private HouseService houseService;

	@Autowired
	private ApartmentService apartmentService;

	@Autowired
	private RegistrationService registrationService;

	@Autowired
	private AppartmentUserRegistrationService apartmentUserRS;

	@Autowired
	private OsbbService osbbService;

	@Autowired
	private UserService userService;

	@Autowired
	private UserRegistrationByAdminDTOMapper userRegistrationByAdminDTOMapper;

	@Autowired
	private GoogleDriveService driveService;

	private EntityResourceList<HouseDTO> buildResources(Iterable<House> houses) {
		EntityResourceList<HouseDTO> resources = new HouseResourceList();
		houses.forEach(house -> {
			HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
			resources.add(toResource(houseDTO));
		});
		return resources;
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public ResponseEntity<EntityResourceList<HouseDTO>> getAllHouses() {
		logger.info("Listing all houses");
		List<House> houses = new ArrayList<House>();
		houses.addAll(houseService.findAll());

		return new ResponseEntity<>(buildResources(houses), HttpStatus.OK);
	}

	@RequestMapping(value = "/all/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<HouseDTO>>> getHousesByOsbbId(@PathVariable(value = "id") Integer id) {
		logger.info("Get all houses by Osbb id: " + id);
		List<House> houses = new ArrayList<>();
		houses.addAll(houseService.getAllHousesByOsbb(id));

		return new ResponseEntity<>(buildResources(houses), HttpStatus.OK);
	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public ResponseEntity<PageDataObject<Resource<HouseDTO>>> listAllHousesByPage(@RequestBody PageParams pageParams) {
		logger.info("get all houses by page number: " + pageParams.getPageNumber());

		return new ResponseEntity<>(buildPageObject(houseService.getAllHouses(buildPageRequest(pageParams))),
				HttpStatus.OK);
	}

	private PageRequest buildPageRequest(PageParams pageParams) {
		return PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
				.addSortedBy(pageParams.getSortedBy(), "street").addOrderType(pageParams.getOrderType())
				.addRows(pageParams.getRowNum()).toPageRequest();
	}

	private PageDataObject<Resource<HouseDTO>> buildPageObject(Page<House> houses) {
		PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(houses);
		return PageDataUtil.providePageData(HousePageDataObject.class, pageSelector, buildResources(houses));
	}

	@RequestMapping(value = "/current", method = RequestMethod.POST)
	public ResponseEntity<PageDataObject<Resource<HouseDTO>>> listHousesByOsbbAndByPage(
			@RequestBody PageParams pageParams, @AuthenticationPrincipal Principal user) {
		User currentUser = userService.findUserByEmail(user.getName());
		final Integer osbbId = currentUser.getOsbb().getOsbbId();

		return new ResponseEntity<>(
				buildPageObject(
						houseService.getAllHousesByOsbb(osbbService.getOsbb(osbbId), buildPageRequest(pageParams))),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}/apartments", method = RequestMethod.GET)
	public ResponseEntity<EntityResourceList<Apartment>> getAllApartmentsByHouseId(
			@PathVariable("id") Integer houseId) {
		logger.info("Get all apartments by house id: " + houseId);
		final EntityResourceList<Apartment> resources = new ApartmentResourceList();
		ResponseEntity<EntityResourceList<Apartment>> response = null;

		final House house = houseService.findHouseById(houseId);
		if (house != null) {
			List<Apartment> apartments = new ArrayList<>();
			apartments.addAll(house.getApartments());

			apartments.forEach(apartment -> {
				logger.info("Apartment number: " + apartment.getNumber());
				Resource<Apartment> resource = resources.createLink(toResource(apartment));
				resources.add(resource);
			});
			response = new ResponseEntity<>(resources, HttpStatus.OK);
		} else {
			logger.error("Error finding house by id: ", houseId);
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public ResponseEntity<EntityResourceList<HouseDTO>> findAllHousesByName(
			@RequestParam(value = "searchParam", required = true) String searchParam) {
		logger.info("searching by searchParam ", searchParam);
		final EntityResourceList<HouseDTO> resources = new HouseResourceList();
		List<House> houses = new ArrayList<>();
		houses.addAll(houseService.getAllHousesBySearchParameter(searchParam));

		houses.forEach((house) -> {
			resources.add(toResource(HouseDTOMapper.mapHouseEntityToDTO(house)));
		});

		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Resource<HouseDTO>> createHouse(@RequestBody HouseDTO houseDTO) {
		logger.info("Adding new house to database");
		House house = HouseDTOMapper.mapHouseDTOToHouse(houseDTO);
		house = houseService.addHouse(house);
		ResponseEntity<Resource<HouseDTO>> response = null;

		if (house == null) {
			logger.error("House was not added to the database");
			response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		} else {
			house.setApartments(createRandomApartments(houseDTO.getApartmentCount(), house));
			houseService.updateHouse(house.getHouseId(), house);
			ResourceLinkCreator<HouseDTO> resourceLinkCreator = new HouseResourceList();
			houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
			Resource<HouseDTO> resource = resourceLinkCreator.createLink(toResource(houseDTO));
			response = new ResponseEntity<>(resource, HttpStatus.OK);
		}
		return response;
	}

	private Set<Apartment> createRandomApartments(Integer total, House house) {
		int apartmentCount = (total == null ? Constants.TOTAL_APARTMENT_NUMBER : total);
		Set<Apartment> apartments = new HashSet<>();

		for (int i = 0; i < apartmentCount; i++) {
			Apartment a = new Apartment();
			a.setNumber(i + 1);
			a.setHouse(house);
			apartments.add(a);
		}

		return apartments;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<Resource<Apartment>> addApartmentToHouse(@PathVariable("id") Integer id,
			@RequestBody Apartment apartment) {
		House house = houseService.findHouseById(id);
		ResponseEntity<Resource<Apartment>> response = null;

		if (house != null) {
			apartment.setHouse(house);
			apartmentService.save(apartment);

			Resource<Apartment> resource = new ApartmentResourceList().createLink(toResource(apartment));
			response = new ResponseEntity<>(resource, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return response;
	}

	@RequestMapping(value = "/withUser/{id}", method = RequestMethod.POST)
	public ResponseEntity<Resource<Apartment>> addApartmentWithUserToHouse(@PathVariable("id") Integer id,
			@RequestBody AppartmentUserDTO apartmentUser) {
		logger.info("Saving user with apartment for house " + id);
		if (apartmentUser == null) {
			logger.warn("No request body");
			throw new InvalidInputDataException("Incoming dto is null");
		}
		Apartment apartment = apartmentUser.getApartment();
		UserRegitrationByAdminDTO userRegitrationByAdminDTO = apartmentUser.getUserRegitrationByAdminDTO();
		if (apartment == null || userRegitrationByAdminDTO == null) {
			logger.warn("incoming data contains null element on place of required ");
			throw new InvalidInputDataException("incoming data contains null element on place of required");
		}
		if (userRegitrationByAdminDTO.getEmail() == null) {
			logger.warn("e-mail was not provided in request body");
			throw new InvalidInputDataException("incoming data contains null where e-mail address supposed to be");
		}
		User foundUser = userService.findUserByEmail(userRegitrationByAdminDTO.getEmail());
		if (foundUser != null) {
			logger.warn("user with e-mail" + userRegitrationByAdminDTO.getEmail() + " already exists");
			throw new UserAlreadyExistsException("user already exists");
		}
		Apartment foundApartment = apartmentService.findByHouseAndNumber(apartment.getNumber(), id);
		if (foundApartment != null) {
			logger.warn("apartment with number " +apartment.getNumber()+" in house with id "+id+" already exists");
			throw new ApartmentAlreadyExistsException("apartment with this number in this house already exists");
		}
		User user = userRegistrationByAdminDTOMapper.mapDTOToEntity(userRegitrationByAdminDTO);
		user.setPassword(registrationService.generatePassword());
		House house = houseService.findHouseById(id);

		ResponseEntity<Resource<Apartment>> response = null;
		if (house == null) {
			logger.warn("house with id "+id+" was not found in DB");
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		checkConnection();
		try {
			apartmentUserRS.registerAppartmentWithUser(user, apartment, house);
		} catch (MessagingException | MailException e) {
			logger.warn("something wrong with internet connection. cannot send mail");
			throw new CannotSendMailException("something wrong with internet connection. cannot send mail");
		} catch (UnknownHostException | InvalidEmailException e) {
			logger.warn("email "+ userRegitrationByAdminDTO.getEmail() + " doesn't match reg-ex or has fake host name");
			throw new WrongEmailException("wrong e-mail");
		}

		Resource<Apartment> resource = new ApartmentResourceList().createLink(toResource(apartment));
		response = new ResponseEntity<>(resource, HttpStatus.OK);
		return response;
	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "upload/{userEmail:.+}", method = RequestMethod.POST)
	public void upload(@PathVariable String userEmail, @RequestParam("file") MultipartFile file) {
		driveService.uploadUserPhoto(file, userEmail);
	}

	@RequestMapping(value = "/street/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<House>> findByStreetId(@PathVariable("id") Integer id) {
		logger.info("Get all house of street: " + id);
		List<House> houses = new ArrayList<>();
		if ((houses = houseService.findAllByStreetId(id)) == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(houses, HttpStatus.OK);
	}

	@RequestMapping(value = "/{houseId}/{apartmentNumber}", method = RequestMethod.GET)
	public boolean isApartmentNumberValid(@PathVariable("houseId") Integer houseId,
			@PathVariable("apartmentNumber") Integer apartmentNumber) {
		boolean valid = true;
		List<Apartment> apartments = new ArrayList<>();
		apartments.addAll(houseService.findAllApartmentsByHouseId(houseId));

		for (Apartment apartment : apartments) {
			if (apartment.getNumber() == apartmentNumber) {
				valid = false;
				break;
			}
		}

		return valid;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Resource<HouseDTO>> getHouseById(@PathVariable("id") Integer houseId) {
		ResponseEntity<Resource<HouseDTO>> response = null;
		House house = houseService.findHouseById(houseId);

		if (house != null) {
			HouseDTO houseDTO = HouseDTOMapper.mapHouseEntityToDTO(house);
			Resource<HouseDTO> resource = new HouseResourceList().createLink(toResource(houseDTO));
			response = new ResponseEntity<>(resource, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

	@RequestMapping(value = "/{houseId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteHouseById(@PathVariable("houseId") Integer houseId) {
		HttpStatus status = HttpStatus.OK;
		logger.info("Deleting house by id: " + houseId);

		if (!houseService.deleteHouseById(houseId)) {
			logger.warn("No house was found by id: " + houseId);
			status = HttpStatus.NOT_MODIFIED;
		} else {
			logger.info("Successfully deleted house with id: " + houseId);
		}

		return new ResponseEntity<>(status);
	}

	@RequestMapping(value = "/numberHouse/{numberHouse}/street/{streetId}", method = RequestMethod.GET)
	public ResponseEntity<House> findByNumberHouseAndStreet(@PathVariable("numberHouse") Integer numberHouse,
			@PathVariable("streetId") Integer streetId) {
		logger.info(" Get house by numberHouse " + numberHouse + " and streetId " + streetId);
		House house;
		if ((house = houseService.getByNumberHouseAndStreet(numberHouse, streetId)) == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(house, HttpStatus.OK);
	}

	@RequestMapping(value = "/image/{fileId}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImageAsResponseEntity(@PathVariable("fileId") String fileId) {
		HttpHeaders headers = new HttpHeaders();
		java.io.InputStream in;
		byte[] media = null;
		try {
			in = driveService.getInput(fileId);
			media = IOUtils.toByteArray(in);
		} catch (IOException e) {
			return new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
		}
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(media, headers, HttpStatus.OK);
		return responseEntity;
	}
	
	private void checkConnection() {
		try {
		InetAddress.getByName("google.com");
		} catch (UnknownHostException e) {
			throw new CannotSendMailException("something wrong with internet connection. cannot send mail");
		}
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "user already exists")
	private static class UserAlreadyExistsException extends RuntimeException {

		UserAlreadyExistsException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "apartment with this number in this house already exists")
	private static class ApartmentAlreadyExistsException extends RuntimeException {

		ApartmentAlreadyExistsException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "something wrong with internet connection. cannot send mail")
	private static class CannotSendMailException extends RuntimeException {

		CannotSendMailException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "wrong e-mail")
	private static class WrongEmailException extends RuntimeException {

		WrongEmailException(String message) {
			super(message);
		}
	}

	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	private static class InvalidInputDataException extends RuntimeException {

		InvalidInputDataException(String message) {
			super(message);
		}
	}
	
	
}
