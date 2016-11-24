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
    
    Apartment save(Apartment apartment);

    void saveApartmentList(List<Apartment> list);

    Apartment findById(Integer id);

    List<Apartment> findAll();

    void delete(Apartment apartment);

    void deleteById(Integer id);

    Apartment update(Apartment apartment);

    Page<Apartment> getByPageNumber(Integer pageNumber, String sortedBy, Boolean ascOrder,Integer number,Integer osbbID);

    Page<Apartment> getByPageNumber(Integer pageNumber, String sortedBy, Boolean ascOrder,Integer number);

}
