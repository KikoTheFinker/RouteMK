package mk.route.routemk.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("principal", auth.getPrincipal().toString());

        return "master";
    }
}
