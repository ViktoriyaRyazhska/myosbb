package com.softserve.osbb.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 04.12.2016
 *
 */
@Entity
@Table(name = "folder")
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;
    private int folder_id;
    private Folder parent_id;
    private Osbb osbb_id;
    private String name;
    private Timestamp created;
    
    public Folder() { }
    
    public Folder(String name, Osbb osbb, Folder parent) {
        this.name = name;
        this.osbb_id = osbb;
        this.parent_id = parent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    public int getId() {
        return folder_id;
    }

    public void setId(int id) {
        this.folder_id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "osbb_id")
    public Osbb getOsbb() {
        return osbb_id;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb_id = osbb;
    }

    @Column(name = "parent_id")
    public Folder getParent() {
        return parent_id;
    }

    public void setParent(Folder parent) {
        this.parent_id = parent;
    }

    @Column(name = "created")
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Folder [id=" + folder_id + ", name=" + name + ", osbb=" + osbb_id.getName() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((osbb_id == null) ? 0 : osbb_id.hashCode());
        result = prime * result + ((parent_id == null) ? 0 : parent_id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Folder other = (Folder) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (osbb_id == null) {
            if (other.osbb_id != null)
                return false;
        } else if (!osbb_id.equals(other.osbb_id))
            return false;
        if (parent_id == null) {
            if (other.parent_id != null)
                return false;
        } else if (!parent_id.equals(other.parent_id))
            return false;
        return true;
    }
    
}
