/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.util.paging;

import java.util.List;

import org.springframework.stereotype.Component;

/**
 * Incorporates info regarding page to be displayed - current page, total number of pages,
 * start and end page (for navigation), rows (data for rows) to be displayed on a page.
 * 
 * Created by nazar.dovhyy on 31.07.2016.
 * @version 1.1
 * @since 16.11.2016
 */
@Component
public class PageDataObject<T> {

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
