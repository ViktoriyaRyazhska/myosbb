/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.VoteDTOForOption;
import com.softserve.osbb.model.Vote;

/**
 * Created by Roman on 16.08.2016.
 */
public class VoteDTOForOptionMapper {

    public static VoteDTOForOption mapVoteEntityToDTO(Vote vote) {
        VoteDTOForOption voteDTO = new VoteDTOForOption();
        
        if (vote != null) {
            voteDTO.setVoteId(vote.getVoteId());
            voteDTO.setDescription(vote.getDescription());
        }
        
        return voteDTO;
    }
}
