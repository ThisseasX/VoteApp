package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import services.VoterService;

@Controller
public class VoteController {

    private final VoterService voterService;

    @Autowired
    public VoteController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping("/vote/{voterId}/{candidateId}/{vote}")
    public String processRequest(
            @PathVariable int voterId,
            @PathVariable int candidateId,
            @PathVariable int vote) {

        voterService.vote(voterId, candidateId, vote);
        return "vote";
    }
}
