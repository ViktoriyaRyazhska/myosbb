package com.softserve.osbb.util.paging.generator;

import com.softserve.osbb.dto.PageParams;
import com.softserve.osbb.utils.Constants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Created by nazar.dovhyy on 17.08.2016.
 */
public class PageRequestGenerator {

    private PageRequestHolder pageRequestHolder;
    private String defaultSortingFiled = "";


    public static class PageSelector {
        private int currentPage;
        private int begin;
        private int end;
        private int totalPages;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getBegin() {
            return begin;
        }

        public void setBegin(int begin) {
            this.begin = begin;
        }

        public int getEnd() {
            return end;
        }

        public void setEnd(int end) {
            this.end = end;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }
    }

    public static class PageRequestHolder {
        private Integer pageNumber;
        private Integer rowNum;
        private String sortedBy;
        private Boolean order;

        public PageRequestHolder(){}

        public PageRequestHolder(Integer pageNumber) {
            this.pageNumber = pageNumber;
        }

        public PageRequestHolder(Integer pageNumber, Integer rowNum) {
            this(pageNumber);
            this.rowNum = rowNum;
        }

        public PageRequestHolder (PageParams pageParams){
            this.pageNumber=pageParams.getPageNumber();
            this.rowNum = pageParams.getRowNum();
            this.sortedBy = pageParams.getSortedBy();
            this.order =  pageParams.getOrderType();
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getRowNum() {
            return rowNum;
        }

        public void setRowNum(int rowNum) {
            this.rowNum = rowNum;
        }

        public String getSortedBy() {
            return sortedBy;
        }

        public void setSortedBy(String sortedBy) {
            this.sortedBy = sortedBy;
        }

        public boolean isOrder() {
            return order;
        }

        public void setOrder(boolean order) {
            this.order = order;
        }
    }

    public PageRequestGenerator(PageRequestHolder pageRequestHolder) {
        this.pageRequestHolder = pageRequestHolder;
    }

    public PageRequestGenerator(PageParams pageParams) {
        this.pageRequestHolder = new PageRequestHolder(pageParams);
    }

    public static PageRequestGenerator generatePageRequest(Integer pageNumber) {
        return new PageRequestGenerator(new PageRequestHolder(pageNumber));
    }

    public PageRequestGenerator addRows(Integer rowNum) {
        this.pageRequestHolder.rowNum = rowNum;
        return this;
    }

    public PageRequestGenerator addSortedBy(String sortedBy, String defaultSorting) {
        this.pageRequestHolder.sortedBy = sortedBy;
        defaultSortingFiled = defaultSorting;
        return this;
    }

    public PageRequestGenerator addOrderType(Boolean order) {
        this.pageRequestHolder.order = order;
        return this;
    }

    public PageRequest toPageRequest() {
        return new PageRequest(addPageNumber(), addRowNum(), addSortingOrderType(), addSortedByField());
    }

    public static <T> PageSelector generatePageSelectorData(Page<T> pageObject) {
        PageSelector pageSelector = new PageSelector();
        pageSelector.setCurrentPage(pageObject.getNumber() + 1);
        pageSelector.setBegin(Math.max(1, pageSelector.getCurrentPage() - 5));
        pageSelector.setTotalPages(pageObject.getTotalPages());
        pageSelector.setEnd(Math.min(pageSelector.getCurrentPage() + 5, pageSelector.getTotalPages()));
        return pageSelector;
    }

    private String addSortedByField() {
        return this.pageRequestHolder.sortedBy == null ?
                this.defaultSortingFiled :
                this.pageRequestHolder.sortedBy;
    }

    private Sort.Direction addSortingOrderType() {
        return getSortingOrder(this.pageRequestHolder.order);
    }

    private int addPageNumber() {
        return this.pageRequestHolder.pageNumber - 1;
    }

    private int addRowNum() {
        return this.pageRequestHolder.rowNum == null ? Constants.DEF_ROWS : this.pageRequestHolder.rowNum;
    }

    private Sort.Direction getSortingOrder(Boolean order) {
        if (order == null) {
            return Sort.Direction.DESC;
        }
        return order ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
