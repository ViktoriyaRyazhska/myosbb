package com.softserve.osbb.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.softserve.osbb.model.enums.Currency;

/**
 * Created by YaroslavStefanyshyn on 02.10.2017.
 */
@Entity
@Table(name = "utilities")
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

	public Utility() {
		super();
	}

	public Integer getUtilityId() {
		return utilityId;
	}

	public void setTariffId(Integer utilityId) {
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
}