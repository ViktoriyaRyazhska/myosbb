package com.softserve.osbb.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Yuri Pushchalo on 16.11.2016.
 */
@Entity
@Table(name = "streets")
public class Street implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private City city;
    private Collection<House> houses;

    public Street(){}

    public Street(Integer id, String name, City city) {
        this.id = id;
        this.name = name;
        this.city = city;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @OneToMany(mappedBy="street",fetch = FetchType.LAZY)
	@JsonIgnore
	public Collection<House> getHouses() {
		return houses;
	}

	public void setHouses(Collection<House> houses) {
		this.houses = houses;
	}
    
    
}
