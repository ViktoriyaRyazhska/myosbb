package com.softserve.osbb.util.paging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by nazar.dovhyy on 31.07.2016.
 */
@Component
public class PageDataObject<T> {

    private static Logger logger = LoggerFactory.getLogger(PageDataObject.class);
    private String currentPage;
    private String totalPages;
    private String beginPage;
    private String endPage;
    private List<T> rows;


    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(String totalPages) {
        this.totalPages = totalPages;
    }

    public String getBeginPage() {
        return beginPage;
    }

    public void setBeginPage(String beginPage) {
        this.beginPage = beginPage;
    }

    public String getEndPage() {
        return endPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
