package com.softserve.osbb.util.paging.impl;

import com.softserve.osbb.dto.BillDTO;
import com.softserve.osbb.util.paging.PageDataObject;
import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by nazar.dovhyy on 18.08.2016.
 */
@Component
public class BillPageDataObject extends PageDataObject<Resource<BillDTO>> {

    private Integer apartmentId;

    public Integer getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public PageDataObject<Resource<BillDTO>> providePageData(PageRequestGenerator.PageSelector pageSelector, List<Resource<BillDTO>> billResourceList) {
        this.setRows(billResourceList);
        this.setCurrentPage(Integer.valueOf(pageSelector.getCurrentPage()).toString());
        this.setBeginPage(Integer.valueOf(pageSelector.getBegin()).toString());
        this.setEndPage(Integer.valueOf(pageSelector.getEnd()).toString());
        this.setTotalPages(Integer.valueOf(pageSelector.getTotalPages()).toString());
        return this;
    }


}
