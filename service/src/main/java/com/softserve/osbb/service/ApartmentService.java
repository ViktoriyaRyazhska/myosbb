package com.softserve.osbb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.softserve.osbb.model.Apartment;

/**
 * Created by Oleg on 12.07.2016.
 */
@Service
public interface ApartmentService {
    
    public abstract Apartment save(Apartment apartment);

    public abstract void saveAll(List<Apartment> list);

    public abstract void delete(Apartment apartment);

    public abstract void deleteById(Integer id);

    public abstract Apartment update(Apartment apartment);

    public abstract Apartment findById(Integer id);

    public abstract List<Apartment> findAll();

    public abstract Page<Apartment> getByPageNumber(Integer pageNumber, String sortedBy,
            Boolean ascOrder, Integer number, Integer osbbID);

    public abstract Page<Apartment> getAllApartmentsToAdmin(Integer pageNumber, String sortedBy,
            Boolean ascOrder, Integer number);
    
}
