package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.VoterService;

@Controller
public class VoteController {

    @PostMapping("/vote")
    public String processRequest(
            @RequestParam("v_afm") String v_afm,
            @RequestParam("c_afm") String c_afm,
            @RequestParam("vote") String vote) {

        VoterService.vote(v_afm, c_afm, Integer.parseInt(vote));
        return "/jsp/vote";
    }
}
