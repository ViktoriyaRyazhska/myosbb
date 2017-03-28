package com.softserve.osbb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.softserve.osbb.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    
    @Query("select ap from Apartment ap join ap.house h join h.osbb o where o.osbbId = :osbbId and ap.number = :number")
    Page<Apartment> findByNumber(Pageable pageable, @Param("number") Integer number, @Param("osbbId")Integer osbbId);

    Apartment findByOwner(Integer owner);//do not delete this!!!
    
    @Query("select ap from Apartment ap join ap.house h  where h.houseId = :houseId and ap.number = :number")
    Apartment findByHouseAndNumber(@Param("number") Integer number, @Param("houseId")Integer houseId);

    @Query("select ap from Apartment ap where ap.number = :number")
    Page<Apartment> findByNumberToAdmin(Pageable pageable, @Param("number") Integer number);

    @Query("select ap from Apartment ap join ap.house h join h.osbb o where o.osbbId = :osbbId")
    Page<Apartment> findAllForUser(Pageable pageable, @Param("osbbId") Integer osbbId);
    
    Page<Apartment> findAll(Pageable pageable);
    
    @Query("select ap from Apartment ap join ap.house h join h.osbb o where o.osbbId = :osbbId")
    List<Apartment> findAllByOsbbId(@Param("osbbId") Integer osbbId);

}
