package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.softserve.osbb.model.enums.Currency;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YaroslavStefanyshyn on 02.10.2017.
 */
@Entity
@Table(name = "utilities")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Utility implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "utility_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer utilityId;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private BigDecimal price;

	@Enumerated(EnumType.STRING)
	@Column(name = "price_currency", columnDefinition = "varchar(45) default 'UAH'")
	private Currency priceCurrency;

	@ManyToOne
	@JoinColumn(name = "osbb_id", referencedColumnName = "osbb_id")
	private Osbb osbb;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Utility parent;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name="house_utility",
			inverseJoinColumns = @JoinColumn(name="house_id", referencedColumnName = "house_id"),
			joinColumns = @JoinColumn(name="utility_id", referencedColumnName = "utility_id")
	)
	private List<House> houses = new ArrayList<>();

	public Utility() {
		super();
	}

	public Integer getUtilityId() {
		return utilityId;
	}

	public void setUtilityId(Integer utilityId) {
		this.utilityId = utilityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Currency getPriceCurrency() {
		return priceCurrency;
	}

	public void setPriceCurrency(Currency priceCurrency) {
		this.priceCurrency = priceCurrency;
	}

	public Osbb getOsbb() {
		return osbb;
	}

	public void setOsbb(Osbb osbb) {
		this.osbb = osbb;
	}

	public Utility getParent() {
		return parent;
	}

	public void setParent(Utility parent) {
		this.parent = parent;
	}

	public List<House> getHouses() {
		return houses;
	}

	public void setHouses(List<House> houses) {
		this.houses = houses;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return "Utility [utilityId=" + utilityId + ", name=" + name + ", description=" + description + ", price="
				+ price + ", priceCurrency=" + priceCurrency + ", osbb=" + osbb + ", parent=" + parent + "]";
	}

}