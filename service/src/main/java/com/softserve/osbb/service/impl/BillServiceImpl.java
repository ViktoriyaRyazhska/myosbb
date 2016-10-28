package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.User;
import com.softserve.osbb.repository.ApartmentRepository;
import com.softserve.osbb.repository.BillRepository;
import com.softserve.osbb.repository.UserRepository;
import com.softserve.osbb.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by nataliia on 11.07.16.
 */

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    ApartmentRepository apartmentRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void saveBillList(List<Bill> list) {
        billRepository.save(list);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Bill findOneBillByID(Integer id) {
        return billRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Bill> findAllBillsByIDs(List<Integer> ids) {
        return billRepository.findAll(ids);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Bill> findAllBills(Pageable pageable) {
        return billRepository.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Bill> findAllByUserId(Integer userId) {
        return billRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Bill> findAllByApartmentOwner(Integer ownerId, Pageable pageable) {
        User apartmentOwner = userRepository.findOne(ownerId);
        Page<Bill> bills = null;
        if (apartmentOwner != null) {
           Apartment ownersApartment = apartmentRepository.findByOwner(apartmentOwner.getUserId());
           bills = billRepository.findByApartment(ownersApartment, pageable);
        }
        return bills;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteBill(Bill bill) {
        billRepository.delete(bill);
    }

    @Override
    public boolean deleteBillByID(Integer id) {
        boolean billExists = billRepository.exists(id);
        if (!billExists) {
            return false;
        }
        billRepository.delete(id);
        return true;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteListBills(List<Bill> list) {
        billRepository.delete(list);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllBills() {
        billRepository.deleteAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long countBills() {
        return billRepository.count();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsBill(Integer id) {
        return billRepository.exists(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Bill> getAllBillsByApartmentWithCurrentMonth(Integer apartmentId){
        return billRepository.getAllBillsByApartmentWithCurrentMonth(apartmentId);
    }
}
