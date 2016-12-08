/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import java.util.ArrayList;
import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import com.softserve.osbb.dto.BillDTO;
import com.softserve.osbb.dto.PageParams;
import com.softserve.osbb.dto.mappers.BillDTOMapper;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.Provider;
import com.softserve.osbb.service.ApartmentService;
import com.softserve.osbb.service.BillService;
import com.softserve.osbb.service.ProviderService;
import com.softserve.osbb.util.filter.FilterList;
import com.softserve.osbb.util.paging.PageDataObject;
import com.softserve.osbb.util.paging.PageDataUtil;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import com.softserve.osbb.util.paging.impl.BillPageDataObject;
import com.softserve.osbb.util.resources.ResourceLinkCreator;
import com.softserve.osbb.util.resources.impl.BillResourceList;
import com.softserve.osbb.util.resources.impl.EntityResourceList;

/**
 * Created by nataliia on 11.07.16.
 */
@RestController
@CrossOrigin
@RequestMapping("/restful/bill")
public class BillController {
	
    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    @Autowired
    private BillService billService;

    @Autowired
    private ApartmentService apartmentService;

    @Autowired
    private ProviderService providerService;

    @Autowired
    private FilterList<Resource<BillDTO>, Bill> filterList;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<BillDTO>> findOneBill(@PathVariable("id") Integer id) {
        logger.info("Fetching bill by id: " + id);
        Bill bill = billService.findOneBillByID(id);
        ResponseEntity<Resource<BillDTO>> response = null;
        
        if (bill == null) {
            logger.warn("Bill with id " + id + " was not found!");
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {        
            ResourceLinkCreator<BillDTO> billDTOResourceLinkCreator = new BillResourceList();
            Resource<BillDTO> billDTOResource = billDTOResourceLinkCreator.createLink(toResource(BillDTOMapper.mapEntityToDTO(bill)));
            response = new ResponseEntity<>(billDTOResource, HttpStatus.OK); 
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PageDataObject<Resource<BillDTO>>> findAllBills(
            @RequestParam(value = "status", required = false) String status,
            @RequestBody PageParams pageParams) {        
        logger.info(String.format("Listing all bills, page number: %d ", pageParams.getPageNumber()));        
        Page<Bill> bills = billService.findAllParentBills(buildPageRequest(pageParams));
        
        return buildResponseEntity(status, bills);
    }

    @RequestMapping(value = "/user/{userId}/all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PageDataObject<Resource<BillDTO>>> listAllBillsByUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "status", required = false) String status,
            @RequestBody PageParams pageParams) {
        
        logger.info(String.format("Listing all bills for user: %d , page number: %d ", userId, pageParams.getPageNumber()));
        Page<Bill> bills = billService.findAllByApartmentOwner(userId, buildPageRequest(pageParams));        
        
        return buildResponseEntity(status, bills);
    }

    private PageRequest buildPageRequest(PageParams pageParams) {
        final PageRequest pageRequest = PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
                .addRows(pageParams.getRowNum())
                .addOrderType(pageParams.getOrderType())
                .addSortedBy(pageParams.getSortedBy(), "date")
                .toPageRequest();
        return pageRequest;
    }

    private ResponseEntity<PageDataObject<Resource<BillDTO>>> buildResponseEntity(String status, Page<Bill> bills) {
        ResponseEntity<PageDataObject<Resource<BillDTO>>> response = null;
        
        if (bills == null || bills.getSize() == 0) {
            logger.error("No bills were found");
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {        
            PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(bills);
            EntityResourceList<BillDTO> billResourceList = (EntityResourceList<BillDTO>) filterList.generateFilteredList(status, bills);
            PageDataObject<Resource<BillDTO>> billPageDataObject = PageDataUtil.providePageData(BillPageDataObject.class, pageSelector, billResourceList);
            response = new ResponseEntity<>(billPageDataObject, HttpStatus.OK);
        }
        
        return response;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<BillDTO> saveBill(@RequestBody BillDTO billDTO) {
        Bill bill = BillDTOMapper.mapDTOtoEntity(billDTO, billService);        
        logger.info("Saving bill " + bill);
        HttpStatus status = HttpStatus.OK;
        Bill parentBill = null;
        
        if(billDTO.getParentBillId() != null) {        	
        	parentBill = billService.findOneBillByID(billDTO.getParentBillId());
        	billDTO.setProviderId(parentBill.getProvider().getProviderId());
        	billDTO.setApartmentId(parentBill.getApartment().getApartmentId());
        }
        Apartment apartment = apartmentService.findById(billDTO.getApartmentId());
        Provider provider = providerService.findOneProviderById(billDTO.getProviderId());      
        if (apartment != null && provider != null) {
            bill.setApartment(apartment);        
            bill.setProvider(provider);
            bill.setParentBill(parentBill);
        
            if (billService.saveBill(bill) == null) {
                logger.warn("Bill wasn't saved");
                status = HttpStatus.CONTINUE;
            } 
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        
        return new ResponseEntity<BillDTO>(status);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<BillDTO>> updateBill(@RequestBody BillDTO billDTO) {
        logger.info("updating bill with id" + billDTO.getBillId());
        Bill bill = BillDTOMapper.mapDTOtoEntity(billDTO, billService);
        HttpStatus status = HttpStatus.OK;
        
        if (billService.saveBill(bill) == null) {
            logger.warn("bill wasn't saved as not found");
            status = HttpStatus.CONTINUE;
        }
        
        return new ResponseEntity<>(status);
    }

    @RequestMapping(value = "/{billId}", method = RequestMethod.DELETE)
    public ResponseEntity<BillDTO> deleteById(@PathVariable("billId") Integer billId) {
        logger.info("deleting bill with id" + billId);
        HttpStatus status = HttpStatus.OK;
        
        if (!billService.deleteBillByID(billId)) {
            logger.warn("Bill was not deleted as not found");
            status = HttpStatus.NOT_FOUND;
        }

        return new ResponseEntity<>(status);
    }
    
    @RequestMapping(value = "/parentbillid", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<Bill>>> findAllParentBill() {
		logger.info("get all parent bills is not null");
		List<Bill> bills = billService.findAllParentBillId();
		List<Resource<Bill>> resources = new ArrayList<>();
		for (Bill temp : bills) {
			resources.add(getBillResource(temp));
		}
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	private Resource<Bill> getBillResource(Bill bill) {
		Resource<Bill> resource = new Resource<>(bill);
		resource.add(linkTo(methodOn(BillController.class).getBill(bill.getBillId().toString())).withSelfRel());
		return resource;
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public Resource<Bill> getBill(@PathVariable(value = "id") String id) {
		logger.info("geting user from database with id=" + id);
		return getBillResource(billService.findOneBillByID(Integer.valueOf(id)));
	}

	@RequestMapping(value = "/parentbillid/subbill/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Resource<Bill>>> findParentBillById(@PathVariable("id") Integer id) {
		logger.info("get all parent bills is not null");
		List<Bill> bills = billService.findAllParentBillById(id);
		List<Resource<Bill>> resources = new ArrayList<>();
		for (Bill temp : bills) {
			resources.add(getBillResource(temp));
		}
		
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

}
