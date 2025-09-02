package mk.route.routemk.web.user;

import mk.route.routemk.services.SearchRoutesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/search-routes")
public class UserRouteController {

    private final SearchRoutesService searchRoutesService;

    public UserRouteController(SearchRoutesService searchRoutesService) {
        this.searchRoutesService = searchRoutesService;
    }

    @GetMapping
    public String findRoutes(@RequestParam(required = false) String from,
                             @RequestParam(required = false) String to,
                             Model model) {

        var result = searchRoutesService.search(from, to);

        model.addAttribute("routes", result.routes());
        model.addAttribute("IndirectRoutes", result.indirectRoutes());
        model.addAttribute("cheapestPricePerRoute", result.cheapestPricePerRoute());
        model.addAttribute("fastestPricePerRoute", result.fastestPricePerRoute());
        model.addAttribute("display", "user/search-routes");

        return "master";
    }
}
