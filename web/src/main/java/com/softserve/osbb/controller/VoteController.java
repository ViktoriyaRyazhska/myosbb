package com.softserve.osbb.controller;

import com.softserve.osbb.dto.VoteDTO;
import com.softserve.osbb.dto.mappers.VoteDTOMapper;
import com.softserve.osbb.model.Option;
import com.softserve.osbb.model.User;
import com.softserve.osbb.model.Vote;
import com.softserve.osbb.service.UserService;
import com.softserve.osbb.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Roman on 13.07.2016.
 */

@CrossOrigin
@RestController
@RequestMapping("/restful/vote")
public class VoteController {

    private static Logger logger = LoggerFactory.getLogger(VoteController.class);

    @Autowired
    private VoteService voteService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Resource<Vote>> createVote(@RequestBody Vote vote) {
        logger.info("Add vote: " + vote);
        User user = userService.findOne(vote.getUser().getUserId());
        user.getVotes().add(vote);
        vote.setUser(user);
        for(Option option: vote.getOptions()) {
            option.setVote(vote);
        }
        vote = voteService.addVote(vote);
        return new ResponseEntity<>(addResourceLinkToVote(vote), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Vote>> getVoteById(@PathVariable("id") Integer id) {
        logger.info("Get vote by id: " + id);
        Vote vote = voteService.getVoteById(id);
        return new ResponseEntity<>(addResourceLinkToVote(vote), HttpStatus.OK);
    }

   @RequestMapping(value="/all/{osbbId}", method = RequestMethod.GET)
   public ResponseEntity<List<Resource<VoteDTO>>> getAllVotesByOsbb(@PathVariable("osbbId") Integer osbbId) {
       logger.info("Get all votes by osbbId: " + osbbId);
       List<Vote> voteList = voteService.getAllVotesByDateOfCreation();
       List<VoteDTO> voteDTOList = VoteDTOMapper.mapAllVoteEntityToDTO(
               voteList.stream().filter(v -> v.getUser().getOsbb().getOsbbId() == osbbId)
                       .collect(Collectors.toList()));
       final List<Resource<VoteDTO>> resourceVoteList = new ArrayList<>();
       for(VoteDTO v: voteDTOList) {
           resourceVoteList.add(addResourceLinkToVote(v));
       }
       return new ResponseEntity<>(resourceVoteList, HttpStatus.OK);
   }

    @RequestMapping(value="/close/{voteId}", method = RequestMethod.GET)
    public ResponseEntity<Resource<Vote>> closeVote(@PathVariable("voteId") Integer voteId) {
        logger.info("Close vote with id: " + voteId);
        Vote v = voteService.getVoteById(voteId);
        v.setAvailable(false);
        Vote updatedVote = voteService.addVote(v);
        return new ResponseEntity<>(addResourceLinkToVote(updatedVote), HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Resource<Vote>> deleteVote(@PathVariable("id") Integer id) {
        logger.info("Delete vote with id: " + id);
        for(Option opt: voteService.getVoteById(id).getOptions()) {
            opt.getUsers().clear();
        }
        voteService.deleteVote(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Resource<Vote> addResourceLinkToVote(Vote vote) {
        if (vote == null) return null;
        Resource<Vote> voteResource = new Resource<>(vote);
        voteResource.add(linkTo(methodOn(VoteController.class)
                .getVoteById(vote.getVoteId()))
                .withSelfRel());
        return voteResource;
    }

    private Resource<VoteDTO> addResourceLinkToVote(VoteDTO vote) {
        if (vote == null) return null;
        Resource<VoteDTO> voteResource = new Resource<>(vote);
        voteResource.add(linkTo(methodOn(VoteController.class)
                .getVoteById(vote.getVoteId()))
                .withSelfRel());
        return voteResource;
    }
}
