package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Contract;
import com.softserve.osbb.repository.ContractRepository;
import com.softserve.osbb.service.ContractService;
import com.softserve.osbb.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roma on 13/07/2016.
 */

@Service
public class ContractServiceImpl implements ContractService {

    private static Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    ContractRepository contractRepository;

    @Transactional(readOnly = false)
    @Override
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Contract findOne(Integer integer) {
        return contractRepository.findOne(integer);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Contract findOne(String id) {
        try {
            return contractRepository.findOne(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean exists(Integer integer) {
        return contractRepository.exists(integer);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Contract> findAll(Sort sort) {
        return contractRepository.findAll(sort);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Contract> findAll(Pageable pageable) {
        return contractRepository.findAll(pageable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Contract> findAll(Iterable<Integer> iterable) {
        return contractRepository.findAll(iterable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long count() {
        return contractRepository.count();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Integer integer) {
        contractRepository.delete(integer);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Contract contract) {
        contractRepository.delete(contract);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void delete(Iterable<? extends Contract> iterable) {
            contractRepository.delete(iterable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAll() {
        contractRepository.deleteAll();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void flush() {
        contractRepository.flush();
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteInBatch(Iterable<Contract> iterable) {
        contractRepository.deleteInBatch(iterable);
    }
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteAllInBatch() {
        contractRepository.deleteAllInBatch();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Contract getOne(Integer integer) {
        return contractRepository.getOne(integer);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Contract saveAndFlush(Contract contract) {
        return contractRepository.saveAndFlush(contract);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public List<Contract> save(Iterable<Contract> iterable) {
        return contractRepository.save(iterable);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Contract> getContracts(Integer pageNumber, String sortBy, Boolean order) {
        logger.info("contract service: getting contracts...");
        logger.info("params: pageNum=" + pageNumber + ", sort=" + sortBy + "order=" + order);
        PageRequest pageRequest = new PageRequest(pageNumber - 1, Constants.DEF_ROWS,
                getSortingOrder(order), sortBy == null ? "dateStart" : sortBy);
        return contractRepository.findAll(pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Page<Contract> findByActiveTrue(Integer pageNumber, String sortBy, Boolean order) {
        logger.info("contract service: getting active contracts...");
        logger.info("params: pageNum=" + pageNumber + ", sort=" + sortBy + "order=" + order);
        PageRequest pageRequest = new PageRequest(pageNumber - 1, Constants.DEF_ROWS,
                getSortingOrder(order), sortBy == null ? "dateStart" : sortBy);
        return contractRepository.findByActiveTrue(pageRequest);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    @Override
    public List<Contract> findByActiveTrue() {
        return contractRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Contract> findContractsByProviderName(String name) {
        return contractRepository.findContractsByProviderName(name);
    }
    public Sort.Direction getSortingOrder(Boolean order) {
        if (order == null) {
            return Sort.Direction.DESC;
        }
        return order == true ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

}
