package com.softserve.osbb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * @author Kostyantyn Panchenko
 * @version 1.0
 * @since 04.12.2016
 *
 */
public class Folder implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private Osbb osbb;
    private Folder parent;
    
    public Folder() { }
    
    public Folder(String name, Osbb osbb, Folder parent) {
        this.name = name;
        this.osbb = osbb;
        this.parent = parent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return osbb;
    }

    public void setOsbb(Osbb osbb) {
        this.osbb = osbb;
    }

    @Column(name = "parent_id")
    public Folder getParent() {
        return parent;
    }

    public void setParent(Folder parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Folder [id=" + id + ", name=" + name + ", osbb=" + osbb.getName()
                + ", parent=" + parent.getName() + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((osbb == null) ? 0 : osbb.hashCode());
        result = prime * result + ((parent == null) ? 0 : parent.hashCode());
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
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (osbb == null) {
            if (other.osbb != null)
                return false;
        } else if (!osbb.equals(other.osbb))
            return false;
        if (parent == null) {
            if (other.parent != null)
                return false;
        } else if (!parent.equals(other.parent))
            return false;
        return true;
    }

}
