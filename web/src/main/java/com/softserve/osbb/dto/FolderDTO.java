package com.softserve.osbb.dto;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 06.12.2016
 *
 */
public class FolderDTO {

    private Integer id;
    private String name;
    
    public FolderDTO() { }

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

    @Override
    public String toString() {
        return "FolderDTO [id=" + id + ", name=" + name + "]";
    }    
    
}
