/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.resources.impl;

import com.softserve.osbb.controller.ReportController;
import com.softserve.osbb.dto.ReportDTO;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by nazar.dovhyy on 18.08.2016.
 */
@SuppressWarnings("serial")
public class ReportResourceList extends EntityResourceList<ReportDTO> {
    
    @Override
    public Resource<ReportDTO> createLink(Resource<ReportDTO> reportResource) {
        ReportDTO report = reportResource.getContent();
        reportResource.add(linkTo(methodOn(ReportController.class)
                .getReportById(report.getReportId())).withSelfRel());

        return reportResource;
    }
}
