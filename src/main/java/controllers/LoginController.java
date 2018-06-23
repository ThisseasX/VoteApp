package controllers;

import models.Voter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.VoterService;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @PostMapping("/login")
    public String processRequest(
            @RequestParam("login-afm") String afm,
            @RequestParam("login-password") String password,
            Model m,
            HttpSession session) {

        Voter v = (Voter) session.getAttribute("voter");

        if (v != null) return "/jsp/vote";
        else v = new Voter(afm);

        boolean loginSuccessful = VoterService.login(v, password);

        if (loginSuccessful) {
            session.setAttribute("voter", v);
            return "/jsp/vote";
        } else {
            m.addAttribute("error", "Login Failed");
            return "home";
        }
    }
}
