package com.softserve.osbb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.osbb.model.enums.Currency;
import com.softserve.osbb.utils.CustomLocalDateDeserializer;
import com.softserve.osbb.utils.CustomLocalDateSerializer;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * Created by Roma on 05.07.2016.
 * Assigned to Anastasiia on 20.07.2016
 */
@Entity
@Table(name = "contract")
public class Contract {
    public static final Currency DEFAULT_CURRENCY = Currency.UAH;

    private Integer contractId;
    private LocalDate dateStart;
    private LocalDate dateFinish;
    private String text;
    private BigDecimal price;
    private Currency priceCurrency;
    private Osbb osbb;
    private Provider provider;
    private boolean active;

    private List<Attachment> attachments;

    @Id
    @Column(name = "contract_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    @Column(name = "date_start")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    public LocalDate getDateStart() {
        return dateStart;
    }

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    @Column(name = "date_finish")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    public LocalDate getDateFinish() {
        return dateFinish;
    }

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    public void setDateFinish(LocalDate dateFinish) {
        this.dateFinish = dateFinish;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency", columnDefinition = "varchar(45) default 'UAH'")
    public Currency getPriceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(Currency priceCurrency) {
        this.priceCurrency = priceCurrency;
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
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id")
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Column(name = "active", columnDefinition = "boolean default true")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "contract_attachment",
            joinColumns = { @JoinColumn(name = "contract_id") }, inverseJoinColumns = { @JoinColumn(name = "attachment_id") })
    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
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
        return "Contract{" +
                "contractId=" + contractId +
                ", dateStart=" + dateStart +
                ", dateFinish=" + dateFinish +
                ", text='" + text + '\'' +
                ", price=" + price +
                ", priceCurrency=" + priceCurrency +
                ", osbb=" + osbb +
                ", provider=" + provider +
                ", active=" + active +
                ", attachments=" + attachments +
                '}';
    }
}
