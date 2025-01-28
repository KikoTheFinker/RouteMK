package mk.route.routemk.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/", "/home"})
public class WelcomeController {
    @GetMapping
    public String welcome() {
        return "master";
    }
}
