/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto;

import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Osbb;
import com.softserve.osbb.model.Street;

import java.util.List;

/**
 * Created by nazar.dovhyy on 03.08.2016.
 */
public class HouseDTO {

    private Integer houseId;
    private Integer numberHouse;
    private Street street;
    private String zipCode;
    private String description;
    private String osbbName;
    private Integer apartmentCount;
    private Integer numberOfInhabitants;

    public HouseDTO() {
        //needed for JSON mapping
    }

    public HouseDTO(HouseDTOBuilder houseDTOBuilder) {
        this.houseId = houseDTOBuilder.houseId;
        this.numberHouse = houseDTOBuilder.numberHouse;
        this.street = houseDTOBuilder.street;
        this.zipCode = houseDTOBuilder.zipCode;
        this.description = houseDTOBuilder.description;
        this.osbbName = houseDTOBuilder.osbbName;
        this.apartmentCount = houseDTOBuilder.apartmentCount;
        this.numberOfInhabitants = houseDTOBuilder.numberOfInhabitants;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public Street getStreet() {
        return street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getDescription() {
        return description;
    }

    public String getOsbbName() {
        return osbbName;
    }

    public Integer getApartmentCount() {
        return apartmentCount;
    }

    public Integer getNumberOfInhabitants() {
        return numberOfInhabitants;
    }

    public Integer getNumberHouse() {
		return numberHouse;
	}




	public static class HouseDTOBuilder {
        private Integer houseId;
        private Integer numberHouse;
        private Street street;
        private String zipCode;
        private String description;
        private String osbbName;
        private Integer apartmentCount;
        private Integer numberOfInhabitants;

        public HouseDTOBuilder setHouseId(final Integer houseId) {
            this.houseId = houseId;
            return this;
        }

        public HouseDTOBuilder setStreet(final Street street) {
            this.street = street;
            return this;
        }

        public HouseDTOBuilder setZipCode(final String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public HouseDTOBuilder setDescription(final String description) {
            this.description = description;
            return this;
        }

        public HouseDTOBuilder setOsbbName(final Osbb osbb) {
            if (osbb != null) {
                this.osbbName = osbb.getName();
            }
            return this;
        }

        public HouseDTOBuilder setApartmentCount(final List<Apartment> apartments) {
            if (apartments != null) {
                this.apartmentCount = apartments.size();
            }
            return this;
        }

        public HouseDTOBuilder setNumberOfInhabitants(List<Apartment> apartments) {
            int inhabitantsCount = 0;
            this.numberOfInhabitants = inhabitantsCount;
            return this;
        }
        
        public HouseDTOBuilder setNumberHouse(Integer numberHouse) {
        	this.numberHouse = numberHouse;
    		return this;
    	}

        public HouseDTO build() {
            return new HouseDTO(this);
        }
    }

    @Override
    public String toString() {
        return "HouseDTO{" +
                "houseId=" + houseId +
                ", street='" + street + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", description='" + description + '\'' +
                ", osbbName='" + osbbName + '\'' +
                ", apartmentCount=" + apartmentCount +
                ", numberOfInhabitants=" + numberOfInhabitants +
                '}';
    }
}
