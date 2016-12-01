package com.softserve.osbb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Anastasiia Fedorak on 7/20/16.
 */

@Entity
@Table(name = "provider_type")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProviderType implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private Integer providerTypeId;
    private String providerTypeName;
    private List<Provider> providers;

    public ProviderType(String providerTypeName) {
        this.providerTypeName = providerTypeName;
    }

    public ProviderType() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "provider_type_id")
    public Integer getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(Integer providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    @Column(name = "type_name")
    public String getProviderTypeName() {
        return providerTypeName;
    }

    public void setProviderTypeName(String providerTypeName) {
        this.providerTypeName = providerTypeName;
    }

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL)
    @JsonIgnore
    public List<Provider> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
    }
}
