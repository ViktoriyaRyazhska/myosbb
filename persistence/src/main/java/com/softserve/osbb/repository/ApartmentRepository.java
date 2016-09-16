package com.softserve.osbb.repository;


import com.softserve.osbb.model.Apartment;

import com.softserve.osbb.model.User;
import org.hibernate.annotations.NamedNativeQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {
    @Query("select ap from Apartment ap join ap.house h join h.osbb o where o.osbbId=:osbbId and ap.number=:number")
    Page<Apartment> findByNumber(Pageable pageable,@Param("number") Integer number,@Param("osbbId")Integer osbbId);

    Apartment findByOwner(Integer owner);//do not delete this!!!


   // @Query("select ap from Apartment ap where ap.number=:osbbId")
   // @Query(value = "SELECT  FROM Apartment  JOIN house ON apartment.house_id=house.house_id\n" +
           // "WHERE house.osbb_id=:osbbId",nativeQuery = true)
    //@Query("select ap from Apartment ap where ap.number=:osbbId")
   @Query("select ap from Apartment ap join ap.house h join h.osbb o where o.osbbId=:osbbId")
    Page<Apartment> findAll(Pageable pageable,@Param("osbbId") Integer osbbId);

    @Query("select ap from Apartment ap where ap.number=1")
    Page<Apartment> findByOsbbId(Integer osbbId,Pageable pageable);

}
