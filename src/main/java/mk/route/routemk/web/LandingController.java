package mk.route.routemk.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class LandingController {

    @GetMapping
    public String landingPage(Model model) {
        model.addAttribute("display", "landing");

        return "master";
    }
}