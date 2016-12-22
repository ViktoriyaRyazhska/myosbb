package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by cavayman on 05.07.2016.
 */
@Entity
@Table(name = "house")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class House implements Serializable {
    
	private static final long serialVersionUID = 1L;
	public static final House NULL = null;
    private Integer houseId;
    private Integer numberHouse;
    private String zipCode;
    private String description;
    private Street street;
    private Osbb osbb;
    private Collection<User> users;
    private Collection<Apartment> apartments;


    public House() {
        //default
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_id")
    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }
    
    @Basic
	@Column(name = "number_house")
	public Integer getNumberHouse() {
		return numberHouse;
	}

	public void setNumberHouse(Integer numberHouse) {
		this.numberHouse = numberHouse;
	}

    @Basic
    @Column(name = "zip_code")
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    public Collection<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Collection<Apartment> appartaments) {
        this.apartments = appartaments;
    }

    @ManyToOne
    @JoinColumn(name = "osbb_id", referencedColumnName = "osbb_id")
    public Osbb getOsbb() {
        return osbb;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb = osbb;
    } 
    
    @ManyToOne
    @JoinColumn(name = "street_id")
    public Street getStreet() {
		return street;
	}

	public void setStreet(Street street) {
		this.street = street;
	}

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    @JsonIgnore
	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "House [houseId=" + houseId + ", numberHouse=" + numberHouse + ", zipCode=" + zipCode + ", description="
				+ description + ", street=" + street + ", osbb=" + osbb + "]";
	}

    


}
