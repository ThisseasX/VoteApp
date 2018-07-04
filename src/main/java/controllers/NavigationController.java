package controllers;

import entities.Candidate;
import entities.Vote;
import entities.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import services.CandidateService;
import services.VoterService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NavigationController {

    private final VoterService voterService;
    private final CandidateService candidateService;

    @Autowired
    public NavigationController(VoterService voterService, CandidateService candidateService) {
        this.voterService = voterService;
        this.candidateService = candidateService;
    }

    @GetMapping("/")
    public String home(HttpSession session, @ModelAttribute("voter") Voter loginVoter) {
        Voter v = (Voter) session.getAttribute("voter");
        if (v != null) return "redirect:/vote";
        else return "home";
    }

    @GetMapping("/vote")
    public String vote(Model m, HttpSession session) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {

            List<Candidate> list = candidateService.getAvailableCandidatesForVoter(voter);

            if (list.size() > 0 && voterService.canVote(voter)) {
                m.addAttribute("list", list);
            } else {
                m.addAttribute("error", "You've reached your voting limit!");
                m.addAttribute("errorPosition", "col-md-6 col-md-offset-3");
            }

            return "vote";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/history")
    public String history(Model m, HttpSession session) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {

            List<Vote> list = voterService.getVoteHistoryForVoter(voter);

            if (list.size() > 0) {
                m.addAttribute("list", list);
            } else {
                m.addAttribute("error", "You haven't voted yet!");
                m.addAttribute("errorPosition", "col-md-6 col-md-offset-3");
            }

            return "history";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/rankings")
    public String rankings(Model m, HttpSession session) {
        Voter voter = (Voter) session.getAttribute("voter");
        if (voter != null) {

            List<Candidate> list = candidateService.getRankings();

            if (list.size() > 0) {
                m.addAttribute("list", list);
            } else {
                m.addAttribute("error", "Nobody has been voted yet!");
                m.addAttribute("errorPosition", "col-md-6 col-md-offset-3");
            }

            return "rankings";
        } else {
            return "redirect:/";
        }
    }
}
