package com.softserve.osbb.model;

import javax.persistence.*;

/**
 * Created by Yuri Pushchalo on 16.11.2016.
 */
@Entity
@Table(name = "streets")
public class Street {
    private Integer id;
    private String name;
    private City city;

    public Street(){}

    public Street(String name, City city) {
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
    
}
