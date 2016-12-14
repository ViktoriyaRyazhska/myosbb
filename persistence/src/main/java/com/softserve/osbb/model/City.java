package com.softserve.osbb.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by Yuri Pushchalo on 15.11.2016.
 */
@Entity
@Table(name = "cities")
public class City implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer id;
    private String name;
    private Region region;
    private Collection<Street> streets ;

    public City() { }

    public City(String name, Region region) {
        this.name = name;
        this.region = region;
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
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    
    @OneToMany(mappedBy="city")
    @JsonIgnore
	public Collection<Street> getStreets() {
		return streets;
	}

	public void setStreets(Collection<Street> streets) {
		this.streets = streets;
	}
    
}
