package com.softserve.osbb.dto;

/**
 * 
 * @author Yuri Pushchalo
 * @version 1.0
 * @since 27.12.2016
 *
 */
public class AddressDTO {

    private Integer id;
    private String name;
    private Integer ownerId;
    
    public AddressDTO() { }
    
    public AddressDTO(Integer id, String name, Integer ownerId) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public String toString() {
        return "AddressDTO [id=" + id + ", name=" + name + ", ownerId=" + ownerId + "]";
    }    
    
}
