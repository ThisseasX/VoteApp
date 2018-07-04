package controllers;

import entities.Voter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import services.VoterService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private final VoterService voterService;

    @Autowired
    public UserController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("voter") Voter voter,
            BindingResult br,
            RedirectAttributes ra,
            HttpSession session) {

        Voter v = (Voter) session.getAttribute("voter");

        if (v != null) return "redirect:/vote";
        if (br.hasErrors()) return "redirect:/";

        Voter loggedVoter = voterService.login(voter);

        if (loggedVoter != null) {
            session.setAttribute("voter", loggedVoter);
            return "redirect:/vote";
        } else {
            ra.addFlashAttribute("error", "Login Failed");
            ra.addFlashAttribute("errorPosition", "col-md-2 col-md-offset-5");
            return "redirect:/";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("voter") Voter voter,
            BindingResult br,
            RedirectAttributes ra,
            HttpSession session) {

        if (br.hasErrors()) return "redirect:/";

        try {
            voterService.register(voter);
            session.setAttribute("voter", voter);
            return "redirect:/vote";

        } catch (Exception e) {
            System.out.println("Unable to register - " + e.getMessage());
            ra.addFlashAttribute("error", "Registration Failed");
            ra.addFlashAttribute("errorPosition", "col-md-2 col-md-offset-5");
            return "redirect:/";
        }
    }
}
