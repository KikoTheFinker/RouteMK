package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search-routes")
public class UserRouteController {
    private final RouteService routeService;

    public UserRouteController(RouteService routeService, TripService tripService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String display(Model model) {
        model.addAttribute("routes", routeService.findAll());
        model.addAttribute("display", "user/search-routes");
        return "master";
    }

    @PostMapping
    public String findRoutesByFromAndTo(@RequestParam(required = false) String from,
                                        @RequestParam(required = false) String to,
                                        Model model) {
        List<Route> filteredRoutes = routeService.findRouteByFromAndToDest(from, to);
        model.addAttribute(filteredRoutes.isEmpty() ? "noRoutesMessage" : "routes", filteredRoutes.isEmpty() ? "No routes found for your search." : filteredRoutes);
        model.addAttribute("display", "user/search-routes");
        return "master";
    }
}