/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto;

/**
 * Created by Roman on 16.08.2016.
 */
public class VoteDTOForOption {
    
    private Integer voteId;
    private String description;

    public VoteDTOForOption() { }

    public VoteDTOForOption(Integer voteId, String description) {
        this.voteId = voteId;
        this.description = description;
    }

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
