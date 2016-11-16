/*
 * Project “OSBB” – a web-application which is a godsend for condominium head, managers and 
 * residents. It offers a very easy way to manage accounting and residents, events and 
 * organizational issues. It represents a simple design and great functionality that is needed 
 * for managing. 
 */
package com.softserve.osbb.dto.mappers;

import com.softserve.osbb.dto.VoteDTO;
import com.softserve.osbb.model.Option;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.Vote;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 14.08.2016.
 */
public class VoteDTOMapper {
    public static VoteDTO mapVoteEntityToDTO(Vote vote) {
        VoteDTO voteDTO = new VoteDTO();
        
        if (vote != null) {
            voteDTO.setVoteId(vote.getVoteId());
            voteDTO.setDescription(vote.getDescription());
            voteDTO.setAvailable(vote.getAvailable());
            voteDTO.setOptions(OptionDTOMapper.mapOptionEntityToDTO(vote.getOptions()));
            voteDTO.setStartTime(vote.getStartTime());
            voteDTO.setEndTime(vote.getEndTime());
            voteDTO.setUser(UserDTOMapper.mapUserEntityToDTO(vote.getUser()));
            voteDTO.setUsersId(getALLUsersIdFromVote(vote.getOptions()));
        }
        
        return voteDTO;
    }

    public static List<VoteDTO> mapAllVoteEntityToDTO(List<Vote> voteList) {
        List<VoteDTO> voteDTOList = new ArrayList<>();
        
        for (Vote v: voteList) {
            voteDTOList.add(mapVoteEntityToDTO(v));
        }
        
        return voteDTOList;
    }

    private static List<Integer> getALLUsersIdFromVote(List<Option> options) {
        List<Integer> usersId = new ArrayList<>();
        
        for(Option option: options) {
            for(User user: option.getUsers()){
                usersId.add(user.getUserId());
            }
        }
        
        return usersId;
    }
}
