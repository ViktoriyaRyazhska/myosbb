/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;

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
import org.springframework.web.bind.annotation.ResponseStatus;
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

    private static Logger logger = LoggerFactory.getLogger(BillController.class);

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
        logger.info("fetching bill by id: " + id);
        Bill bill = billService.findOneBillByID(id);
        
        if (bill == null) {
            logger.warn("no bill was found with id: " + id);
            throw new BillNotFoundException();
        }
        
        ResourceLinkCreator<BillDTO> billDTOResourceLinkCreator = new BillResourceList();
        Resource<BillDTO> billDTOResource = billDTOResourceLinkCreator
                .createLink(toResource(BillDTOMapper.mapEntityToDTO(bill)));
        return new ResponseEntity<>(billDTOResource, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PageDataObject<Resource<BillDTO>>> findAllBills(
            @RequestParam(value = "status", required = false) String status,
            @RequestBody PageParams pageParams) {
        logger.info(String.format("listing all bills, page number: %d ", pageParams.getPageNumber()));
        final PageRequest pageRequest = PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
                .addRows(pageParams.getRowNum())
                .addOrderType(pageParams.getOrderType())
                .addSortedBy(pageParams.getSortedBy(), "date")
                .toPageRequest();
        Page<Bill> bills = billService.findAllBills(pageRequest);
        
        if (bills == null || bills.getSize() == 0) {
            logger.error("no bills were found");
            throw new BillNotFoundException();
        }
        
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(bills);
        EntityResourceList<BillDTO> billResourceList = (EntityResourceList<BillDTO>) filterList.generateFilteredList(status, bills);
        PageDataObject<Resource<BillDTO>> billPageDataObject = PageDataUtil.providePageData(BillPageDataObject.class, pageSelector, billResourceList);
        
        return new ResponseEntity<>(billPageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{userId}/all", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<PageDataObject<Resource<BillDTO>>> listAllBillsByUser(
            @PathVariable("userId") Integer userId,
            @RequestParam(value = "status", required = false) String status,
            @RequestBody PageParams pageParams) {
        logger.info(String.format("listing all bills for user: %d , page number: %d ", userId, pageParams.getPageNumber()));
        final PageRequest pageRequest = PageRequestGenerator.generatePageRequest(pageParams.getPageNumber())
                .addRows(pageParams.getRowNum())
                .addOrderType(pageParams.getOrderType())
                .addSortedBy(pageParams.getSortedBy(), "date")
                .toPageRequest();
        Page<Bill> bills = billService.findAllByApartmentOwner(userId, pageRequest);
        
        if (bills == null || bills.getSize() == 0) {
            logger.error("no bills were found");
            throw new BillNotFoundException();
        }
        
        PageRequestGenerator.PageSelector pageSelector = PageRequestGenerator.generatePageSelectorData(bills);
        EntityResourceList<BillDTO> billResourceList = (EntityResourceList<BillDTO>) filterList.generateFilteredList(status, bills);
        PageDataObject<Resource<BillDTO>> billPageDataObject = PageDataUtil.providePageData(BillPageDataObject.class, pageSelector, billResourceList);
        
        return new ResponseEntity<>(billPageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<BillDTO> saveBill(@RequestBody BillDTO billDTO) {
        logger.info("saving bill");
        Bill bill = BillDTOMapper.mapDTOtoEntity(billDTO, billService);        
        logger.info("bill" + bill);
        
        Apartment apartment = apartmentService.findOneApartmentByID(billDTO.getApartmentId());
        Provider provider = providerService.findOneProviderById(billDTO.getProviderId());
        bill.setApartment(apartment);
        bill.setProvider(provider);
        bill = billService.saveBill(bill);
        
        if (bill == null) {
            logger.warn("bill wasn't saved");
            return new ResponseEntity<>(HttpStatus.CONTINUE);
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<Resource<BillDTO>> updateBill(@RequestBody BillDTO billDTO) {
        logger.info("updating bill with id" + billDTO.getBillId());
        Bill bill = BillDTOMapper.mapDTOtoEntity(billDTO, billService);
        bill = billService.saveBill(bill);
        
        if (bill == null) {
            logger.warn("bill wasn't saved as not found");
            throw new BillNotFoundException();
        }
        
        logger.info("successfully updated bill with id " + bill.getBillId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{billId}", method = RequestMethod.DELETE)
    public ResponseEntity<BillDTO> deleteById(@PathVariable("billId") Integer billId) {
        logger.info("deleting bill with id" + billId);
        boolean isDeleted = billService.deleteBillByID(billId);
        
        if (!isDeleted) {
            logger.warn("bill was not deleted as not found");
            throw new BillNotFoundException();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }    

    @SuppressWarnings("serial")
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Bills not found")
    private class BillNotFoundException extends RuntimeException { }

}
