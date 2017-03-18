package com.softserve.osbb.repository;

import com.softserve.osbb.model.House;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<House, Integer> {

    List<House> findByStreetId(Integer Integer);
    
    @Query(value = "Select * from house where house.number_house = :numberHouse and house.street_id = :streetId", nativeQuery = true)
    House getByNumberHouseAndStreet(@Param("numberHouse") Integer numberHouse, @Param("streetId") Integer streetId);

    @Query("Select h From House h where LOWER(h.street) LIKE LOWER(CONCAT('%',?1,'%'))"
            + "OR LOWER(h.description) LIKE LOWER(CONCAT('%',?1,'%'))"
            + "OR LOWER(h.zipCode) LIKE LOWER(CONCAT('%',?1,'%'))")
    List<House> getAlReportsBySearchParameter(String searchTerm);

    @Query("select h from House h join h.osbb o where o.osbbId = :osbbId")
    List<House> findByOsbbId(@Param("osbbId") Integer osbbId);

    Page<House> findByOsbb(Osbb osbb, Pageable pageable);

    House findByUsers(User user);
}
