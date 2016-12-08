/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.softserve.osbb.model.Apartment;
import com.softserve.osbb.model.Bill;
import com.softserve.osbb.model.Provider;
import com.softserve.osbb.model.enums.BillStatus;
import com.softserve.osbb.utils.CustomLocalDateDeserializer;
import com.softserve.osbb.utils.CustomLocalDateSerializer;

import java.time.LocalDate;

/**
 * Created by nazar.dovhyy on 18.08.2016.
 */
public class BillDTO {
    
    private Integer billId;
    private String name;
    private LocalDate date;
    private Float tariff;
    private Float toPay;
    private Float paid;
    private Integer apartmentNumber;
    private Integer apartmentId;
    private Integer providerId;
    private String status;
    private Integer parentBillId;

    public BillDTO() { }

    public BillDTO(BillDTOBuilder billDTOBuilder) {
        this.billId = billDTOBuilder.billId;
        this.name = billDTOBuilder.name;
        this.date = billDTOBuilder.date;
        this.tariff = billDTOBuilder.tariff;
        this.toPay = billDTOBuilder.toPay;
        this.paid = billDTOBuilder.paid;
        this.apartmentNumber = billDTOBuilder.apartmentNumber;
        this.status = billDTOBuilder.status;
        this.parentBillId = billDTOBuilder.parentBillId;
    }

    public Integer getBillId() {
        return billId;
    }
    
    public String getName() {
        return name;
    }

    @JsonSerialize(using = CustomLocalDateSerializer.class)
    public LocalDate getDate() {
        return date;
    }

    public Float getTariff() {
        return tariff;
    }

    public Float getToPay() {
        return toPay;
    }

    public Float getPaid() {
        return paid;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public String getStatus() {
        return status;
    }

    public Integer getApartmentId() {
        return apartmentId;
    }
    
    public Integer getParentBillId() {
        return parentBillId;
    }

    public void setApartmentId(Integer apartmentId) {
        this.apartmentId = apartmentId;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @JsonDeserialize(using = CustomLocalDateDeserializer.class)
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTariff(Float tariff) {
        this.tariff = tariff;
    }

    public void setToPay(Float toPay) {
        this.toPay = toPay;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setParentBillId(Integer parentBillId) {
        this.parentBillId = parentBillId;
    }

    public static class BillDTOBuilder {
        private Integer billId;
        private String name;
        private LocalDate date;
        private Float tariff;
        private Float toPay;
        private Float paid;
        @SuppressWarnings("unused")
        private Integer apartmentId;
        @SuppressWarnings("unused")
        private Integer providerId;
        private Integer apartmentNumber;
        private String status;
        //@SuppressWarnings("unused")
        private Integer parentBillId;

        public BillDTOBuilder() { }

        public BillDTOBuilder setBillId(Integer billId) {
            this.billId = billId;
            return this;
        }
        
        public BillDTOBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BillDTOBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public BillDTOBuilder setTariff(Float tariff) {
            this.tariff = tariff;
            return this;
        }

        public BillDTOBuilder setToPay(Float toPay) {
            this.toPay = toPay;
            return this;
        }

        public BillDTOBuilder setPaid(Float paid) {
            this.paid = paid;
            return this;
        }

        public BillDTOBuilder setApartmentId(Apartment apartment) {
            if (apartment != null) {
                this.apartmentId = apartment.getApartmentId();
            }
            return this;
        }

        public BillDTOBuilder setProviderId(Provider provider) {
            if (provider != null) {
                this.providerId = provider.getProviderId();
            }
            return this;
        }

        public BillDTOBuilder setApartmentNumber(Apartment apartment) {
            if (apartment != null) {
                this.apartmentNumber = apartment.getNumber();
            }
            return this;
        }

        public BillDTOBuilder setStatus(BillStatus billStatus) {
            if (billStatus != null) {
                this.status = billStatus.toString();
            }
            return this;
        }
        
        public BillDTOBuilder setParentBillId(Bill parentBill) {
            if (parentBill != null) {
                this.parentBillId = parentBill.getBillId();
            }
            return this;
        }

        public BillDTO build() {
            return new BillDTO(this);
        }
    }

    @Override
    public String toString() {
        return "BillDTO{" +
                "billId=" + billId +
                ", date=" + date +
                ", tariff=" + tariff +
                ", toPay=" + toPay +
                ", paid=" + paid +
                ", apartmentNumber=" + apartmentNumber +
                '}';
    }
}
