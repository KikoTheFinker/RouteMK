package mk.route.routemk.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class RoutesController {

    @GetMapping("/routes")
    public String routes() {
        return "/company/routes";
    }
}
