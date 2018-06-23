package controllers;

import models.Voter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import services.VoterService;
import utils.StringUtils;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController {

    @PostMapping("/register")
    public String processRequest(
            @RequestParam("register-afm") String afm,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("register-password") String password,
            Model m,
            HttpSession session) {

        Voter v = new Voter(
                afm,
                StringUtils.capitalize(name),
                StringUtils.capitalize(surname)
        );

        boolean isRegisterSuccessful = VoterService.register(v, password);

        if (isRegisterSuccessful) {
            session.setAttribute("voter", v);
            return "/jsp/vote";
        } else {
            m.addAttribute("error", "Registration Failed");
            return "home";
        }
    }
}
