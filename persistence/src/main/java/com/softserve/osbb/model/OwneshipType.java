package com.softserve.osbb.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ownership")
public class OwneshipType implements Serializable{
	private static final long serialVersionUID = 1L;
	public static final OwneshipType NULL = null;
	
	private Integer ownershipId;
	private String type;
	 
	 
	
	public OwneshipType() {
		
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownership_id")
	public Integer getId() {
		return ownershipId;
	}
	public void setId(Integer id) {
		this.ownershipId = id;
	}
	
	@Basic
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	
	 @Override
	    public int hashCode() {
	        return HashCodeBuilder.reflectionHashCode(this);
	    }

}
