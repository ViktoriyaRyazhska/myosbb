package com.softserve.osbb.util.filter.impl;

import com.softserve.osbb.dto.BillDTO;
import com.softserve.osbb.dto.mappers.BillDTOMapper;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.enums.BillStatus;
import com.softserve.osbb.util.filter.FilterList;
import com.softserve.osbb.util.resources.impl.BillResourceList;
import com.softserve.osbb.util.resources.impl.EntityResourceList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.softserve.osbb.util.resources.util.ResourceUtil.toResource;

/**
 * Created by nazar.dovhyy on 04.09.2016.
 */
@Component
public class BillFilterList implements FilterList<Resource<BillDTO>, Bill> {

    private static final String PAID = "PAID";
    private static final String NOT_PAID = "NOT_PAID";
    private static Logger logger = LoggerFactory.getLogger(BillFilterList.class);

    @Override
    public List<Resource<BillDTO>> generateFilteredList(String status, Page<Bill> bills) {
        EntityResourceList<BillDTO> billResourceList = new BillResourceList();
        if (status == null) {
            logger.info("default filtering");
            addIfNoStatus(bills, billResourceList);
            return billResourceList;
        }
        processFilterByStatus(status, bills, billResourceList);
        return billResourceList;
    }

    private void processFilterByStatus(String status, Page<Bill> bills, EntityResourceList<BillDTO> billResourceList) {
        switch (status.toUpperCase()) {
            case PAID:
                addByStatusIfPaid(bills, billResourceList);
                break;
            case NOT_PAID:
                addByStatusIfNotPaid(bills, billResourceList);
                break;
            default:
                addIfNoStatus(bills, billResourceList);
                break;

        }
    }

    private void addByStatusIfNotPaid(Page<Bill> bills, EntityResourceList<BillDTO> billResourceList) {
        logger.info("filtering by: " + BillStatus.NOT_PAID);
        bills.getContent()
                .stream()
                .filter((b) -> b.getBillStatus() == BillStatus.NOT_PAID)
                .forEach((bill) -> {
                    BillDTO billDTo = BillDTOMapper.mapEntityToDTO(bill);
                    logger.info("billDto created " + billDTo.toString());
                    billResourceList.add(toResource(billDTo));
                });
    }

    private void addByStatusIfPaid(Page<Bill> bills, EntityResourceList<BillDTO> billResourceList) {
        logger.info("filtering by: " + BillStatus.PAID);
        bills.getContent()
                .stream()
                .filter((b) -> b.getBillStatus() == BillStatus.PAID)
                .forEach((bill) -> {
                    BillDTO billDTo = BillDTOMapper.mapEntityToDTO(bill);
                    logger.info("billDto created " + billDTo.toString());
                    billResourceList.add(toResource(billDTo));
                });
    }

    private void addIfNoStatus(Page<Bill> bills, EntityResourceList<BillDTO> billResourceList) {
        bills.forEach((bill) -> {
                    BillDTO billDTo = BillDTOMapper.mapEntityToDTO(bill);
                    logger.info("billDto created " + billDTo.toString());
                    billResourceList.add(toResource(billDTo));
                }
        );
    }
}
