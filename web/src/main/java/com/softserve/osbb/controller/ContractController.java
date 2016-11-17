/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

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

import com.softserve.osbb.model.Attachment;
import com.softserve.osbb.model.Contract;
import com.softserve.osbb.model.Provider;
import com.softserve.osbb.service.AttachmentService;
import com.softserve.osbb.service.ContractService;
import com.softserve.osbb.service.ProviderService;
import com.softserve.osbb.util.paging.PageDataObject;

/**
 * Created by Roma on 13/07/2016.
 * Assigned to Anastasiia Fedorak 20/07/2016
 */
@RestController
@RequestMapping(value = "/restful/contract")
@CrossOrigin
public class ContractController {
    
    private static Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    ContractService contractService;

    @Autowired
    ProviderService providerService;

    @Autowired
    AttachmentService attachmentService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Resource<Contract>> getAll() {
        logger.info("Getting all contracts from database");
        List<Contract> contracts = contractService.findAll();
        
        logger.info("Passed ContractService");
        List<Resource<Contract>> resources = new ArrayList<Resource<Contract>>();
        
        for (Contract temp : contracts) {
            resources.add(getContractResource(temp));
        }
        
        return resources;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Contract>>> listAllContracts(
            @RequestParam(value = "pageNum", required = true) Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder,
            @RequestParam(value = "actv", required = false) Boolean onlyActive) {

        logger.info("getting all contracts by page number: " + pageNumber);
        Page<Contract> contractsByPage = null;
        contractsByPage = contractService.getContracts(pageNumber, sortedBy, ascOrder);
        
        if ((onlyActive == null) ? false : onlyActive) {
            logger.warn("getting only active contracts");
            contractsByPage = contractService.findByActiveTrue(pageNumber, sortedBy, ascOrder);
        }
        
        int currentPage = contractsByPage.getNumber() + 1;
        logger.info("current page : " + currentPage);
        
        int begin = Math.max(1, currentPage - 5);
        logger.info("starts with: " + begin);
        
        int totalPages = contractsByPage.getTotalPages();
        int end = Math.min(currentPage + 5, totalPages);
        logger.info("ends with: " + totalPages);

        List<Resource<Contract>> resourceList = new ArrayList<>();
        contractsByPage.forEach((contract) -> {
            resourceList.add(getContractResource(contract));
        });

        PageDataObject<Resource<Contract>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(Integer.valueOf(currentPage).toString());
        pageDataObject.setBeginPage(Integer.valueOf(begin).toString());
        pageDataObject.setEndPage(Integer.valueOf(end).toString());
        pageDataObject.setTotalPages(Integer.valueOf(totalPages).toString());

        return new ResponseEntity<>(pageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Contract>>> getContractsByProviderName(
            @RequestParam(value = "name", required = true) String name) {
        
        List<Contract> contractsBySearchTerm = contractService.findContractsByProviderName(name);
        List<Resource<Contract>> resourceContractList = new ArrayList<>();
        
        contractsBySearchTerm.stream().forEach((contract) -> {
            resourceContractList.add(getContractResource(contract));
        });
        
        return new ResponseEntity<>(resourceContractList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Resource<Contract> getContract(@PathVariable(value = "id") String id) {
        logger.info("getting contract from database with id=" + id);
        return getContractResource(contractService.findOne(id));
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Contract putContract(@RequestBody Contract contract) {
        logger.info("Saving contract, sending to service" + contract);
        if (contract.getPriceCurrency() == null) {
            contract.setPriceCurrency(Contract.DEFAULT_CURRENCY);
        }
        
        ArrayList<Attachment> list = new ArrayList<>();
        for (int i = 0; i < contract.getAttachments().size(); i++) {
            list.add(attachmentService.getAttachmentById(contract.getAttachments().get(i).getAttachmentId()));
        }
        
        contract.setAttachments(list); //tmp dirty solution PersistentObjectException, detached Attachment entity passed to persist
        Contract newContract = contractService.save(contract);
        
        if (newContract != null) {
            Provider provider = providerService.findOneProviderById(newContract.getProvider().getProviderId());
            provider.setActive(true);
            providerService.saveProvider(provider);
            
            if (!provider.isActive()) {
                try {
                    providerService.updateProvider(provider.getProviderId(), provider);
                } catch (Exception e) {
                    logger.error("cannot update provider state");
                    logger.error(e.getMessage());
                }
            }
        }
        
        return newContract;
    }

    @RequestMapping(value="/{id}",method=RequestMethod.POST)
    public Contract updateContract(@PathVariable Integer id, @RequestBody Contract contract) {
        logger.info("Updating contract id:"+id);
        contract.setContractId(id);
        return contractService.saveAndFlush(contract);
    }

    @RequestMapping(value="/",method=RequestMethod.DELETE)
    public boolean deleteAllContracts(@RequestBody Contract contract) {
        logger.warn("Deleting all Contracts");
        contractService.deleteAll();
        return true;
    }
    
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public boolean deleteContractByID(@PathVariable(value ="id") Integer id)
    {logger.warn("Deleting contract with id:"+id);
        contractService.delete(id);
        return true;
    }

    private Resource<Contract> getContractResource(Contract contract) {
        Resource<Contract> resource = new Resource<Contract>(contract);
        resource.add(linkTo(methodOn(ContractController.class).getContract(contract.getContractId().toString())).withSelfRel());
        return resource;
    }

}
