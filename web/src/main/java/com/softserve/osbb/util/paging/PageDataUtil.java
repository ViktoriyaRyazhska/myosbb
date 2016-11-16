/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.paging;

import com.softserve.osbb.util.paging.generator.PageRequestGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by nazar.dovhyy on 11.09.2016.
 */
public final class PageDataUtil {

    private static Logger logger = LoggerFactory.getLogger(PageDataUtil.class);

    public static <T> PageDataObject<T> providePageData(Class<? extends PageDataObject<T>> pageDataObjectType, 
            PageRequestGenerator.PageSelector pageSelector, List<T> billResourceList) {
        
        PageDataObject<T> pageDataObject = null;
        try {
            pageDataObject = pageDataObjectType.newInstance();
            pageDataObject.setRows(billResourceList);
            pageDataObject.setCurrentPage(Integer.valueOf(pageSelector.getCurrentPage()).toString());
            pageDataObject.setBeginPage(Integer.valueOf(pageSelector.getBegin()).toString());
            pageDataObject.setEndPage(Integer.valueOf(pageSelector.getEnd()).toString());
            pageDataObject.setTotalPages(Integer.valueOf(pageSelector.getTotalPages()).toString());
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("error creating PageDataObject of class " + pageDataObjectType);
        }
        return pageDataObject;
    }
}
