/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.controller;

import com.softserve.osbb.model.Attachment;
import com.softserve.osbb.model.enums.AttachmentType;
import com.softserve.osbb.service.AttachmentService;
import com.softserve.osbb.util.paging.PageDataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nataliia on 11.07.16.
 */
@CrossOrigin
@RestController
@RequestMapping("/restful/attachment")
public class AttachmentController {

    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private AttachmentService attachmentService;

    @SuppressWarnings("unused")
    private final ResourceLoader resourceLoader;

    @Autowired
    public AttachmentController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFileToServer(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                logger.info("Uploading file " + file.getOriginalFilename());
                return ResponseEntity.status(HttpStatus.OK).body(attachmentService.uploadFile(file));
            } catch (RuntimeException e) {
                logger.warn("Could not upload file " + file.getOriginalFilename());
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        } else {
            logger.warn("Could not upload file " + file.getOriginalFilename() + " because it is empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/logo", method = RequestMethod.POST)
    public ResponseEntity<Resource<Attachment>> uploadLogo(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                logger.info("Uploading logo " + file.getOriginalFilename());
                Attachment attachment = attachmentService.uploadFile(file);
                attachment.setType(AttachmentType.IMAGE);
                
                Resource<Attachment> attachmentResource = new Resource<>(attachment);
                attachmentResource = getResourceWithLink(attachmentResource);
                
                return new ResponseEntity<>(attachmentResource, HttpStatus.OK);
            } catch (RuntimeException e) {
                logger.warn("Could not upload logo " + file.getOriginalFilename());
                return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
            }
        } else {
            logger.warn("Could not upload logo " + file.getOriginalFilename() + " because it is empty.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Attachment>>> findAllAttachments() {
        logger.info("Getting all attachments.");
        List<Attachment> attachmentList = attachmentService.getAllAttachments();
        final List<Resource<Attachment>> resourceAttachmentList = new ArrayList<>();
        attachmentList.forEach(attachment -> resourceAttachmentList.add(getResourceWithLink(toResource(attachment))));
        return new ResponseEntity<>(resourceAttachmentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/last/{count}", method = RequestMethod.GET)
    public List<Attachment> findLast(@PathVariable("count") Integer count) {
        List<Attachment> allAttachments = attachmentService.getAllAttachments();
        return allAttachments.subList(allAttachments.size() - count, allAttachments.size());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Attachment>> findAttachmentById(@PathVariable("id") Integer attachmentId) {
        logger.info("Getting attachment by id: " + attachmentId);
        Attachment attachment = attachmentService.getAttachmentById(attachmentId);
        Resource<Attachment> attachmentResource = new Resource<>(attachment);
        attachmentResource = getResourceWithLink(attachmentResource);
        return new ResponseEntity<>(attachmentResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<Resource<Attachment>> updateAttachment(@RequestBody Attachment attachment) {
        logger.info("Updating attachment by id: " + attachment.getAttachmentId());
        attachment = attachmentService.updateAttachment(attachment.getAttachmentId(), attachment);
        Resource<Attachment> attachmentResource = new Resource<>(attachment);
        attachmentResource = getResourceWithLink(attachmentResource);
        return new ResponseEntity<>(attachmentResource, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Attachment> deleteAttachmentEverywhere(@PathVariable("id") Integer attachmentId) {
        logger.info("Removing attachment by id: " + attachmentId);
        attachmentService.deleteAttachmentEverywhere(attachmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public ResponseEntity<Attachment> deleteAllAttachments() {
        logger.info("Removing all attachments.");
        attachmentService.deleteAllAttachments();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<PageDataObject<Resource<Attachment>>> listAllAttachments(
            @RequestParam(value = "pageNumber") Integer pageNumber,
            @RequestParam(value = "sortedBy", required = false) String sortedBy,
            @RequestParam(value = "asc", required = false) Boolean ascOrder) {
        logger.info("Get all attachment by page number: " + pageNumber);
        Page<Attachment> attachmentsByPage = attachmentService.getAllAttachments(pageNumber, sortedBy, ascOrder);

        Integer currentPage = attachmentsByPage.getNumber() + 1;
        Integer begin = Math.max(1, currentPage - 5);
        Integer totalPages = attachmentsByPage.getTotalPages();
        Integer end = Math.min(currentPage + 5, totalPages);

        List<Resource<Attachment>> resourceList = new ArrayList<>();
        attachmentsByPage.forEach((attachment) -> resourceList.add(getResourceWithLink(toResource(attachment))));

        PageDataObject<Resource<Attachment>> pageDataObject = new PageDataObject<>();
        pageDataObject.setRows(resourceList);
        pageDataObject.setCurrentPage(currentPage.toString());
        pageDataObject.setBeginPage(begin.toString());
        pageDataObject.setEndPage(end.toString());
        pageDataObject.setTotalPages(totalPages.toString());

        return new ResponseEntity<>(pageDataObject, HttpStatus.OK);
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResponseEntity<List<Resource<Attachment>>> getAttachmentsByPath(
            @RequestParam(value = "path") String path) {
        logger.info("Fetching attachment by search parameter: " + path);
        List<Attachment> attachmentsBySearchTerm = attachmentService.findAttachmentByPath(path);
        
        if (attachmentsBySearchTerm.isEmpty()) {
            logger.warn("No attachments were found.");
        }
        
        List<Resource<Attachment>> resourceAttachmentList = new ArrayList<>();
        attachmentsBySearchTerm.forEach((attachment) -> resourceAttachmentList.add(getResourceWithLink(toResource(attachment))));
        
        return new ResponseEntity<>(resourceAttachmentList, HttpStatus.OK);
    }

    private Resource<Attachment> getResourceWithLink(Resource<Attachment> attachmentResource) {
        attachmentResource.add(linkTo(methodOn(AttachmentController.class)
                .findAttachmentById(attachmentResource.getContent().getAttachmentId()))
                .withSelfRel());
        return attachmentResource;
    }
}
