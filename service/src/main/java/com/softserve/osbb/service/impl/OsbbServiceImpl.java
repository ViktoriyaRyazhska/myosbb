package com.softserve.osbb.service.impl;

import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.repository.OsbbRepository;
import com.softserve.osbb.service.OsbbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 08.07.2016.
 */
@Service
public class OsbbServiceImpl implements OsbbService {

    @Autowired
    private OsbbRepository osbbRepository;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Osbb addOsbb(Osbb osbb) {
        if(osbb == null) {
            return null;
        }
        return osbbRepository.saveAndFlush(osbb);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Osbb getOsbb(Integer id) {
        return osbbRepository.findOne(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Osbb getOsbb(String name) {
        return osbbRepository.findByName(name);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Osbb> getAllOsbb() {
        return osbbRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Osbb> getAllByOrder(String sortedBy, Boolean ascOrder) {
        if(sortedBy == null || ascOrder == null) {
            return osbbRepository.findAll();
        } else {
            Sort sort = new Sort(getSortDirection(ascOrder), sortedBy);
            return osbbRepository.findAll(sort);
        }
    }

    private Sort.Direction getSortDirection(Boolean order) {
        return order == true ? Sort.Direction.DESC : Sort.Direction.ASC;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Osbb> findByAvailable(Boolean available) {
        return osbbRepository.findByAvailable(available);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public List<Osbb> findByNameContaining(String name) {
        return osbbRepository.findByNameContainingIgnoreCase(name);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public long countOsbb() {
        return osbbRepository.count();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public boolean existsOsbb(Integer id) {
        return osbbRepository.exists(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public Osbb updateOsbb(Osbb osbb){
        if(osbbRepository.exists(osbb.getOsbbId())) {
            return osbbRepository.save(osbb);
        } else {
            throw new IllegalArgumentException("Osbb with id=" + osbb.getOsbbId()
                    + " doesn't exist. First try to add this osbb.");
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteOsbb(Integer id) {
        osbbRepository.delete(id);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void deleteOsbb(Osbb osbb) {
        osbbRepository.delete(osbb);
    }

}
