package mk.route.routemk.web.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage(Model model) {
        model.addAttribute("display", "login");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("principal", auth.getPrincipal().toString());

        return "master";
    }

}
