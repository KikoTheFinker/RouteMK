package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search-routes")
public class UserRouteController {
    private final RouteService routeService;
    private final LocationService locationService;

    public UserRouteController(RouteService routeService, LocationService locationService) {
        this.routeService = routeService;
        this.locationService = locationService;
    }

    @GetMapping
    public String findRoutesByFromAndTo(@RequestParam(required = false) String from,
                                        @RequestParam(required = false) String to,
                                        Model model) {

        List<Route> filteredRoutes = ((from == null || from.isBlank()) && (to == null || to.isBlank()))
                ? routeService.findAll()
                : routeService.findRouteByFromAndToDest(from, to);

        if (from != null && to != null && !from.isBlank() && !to.isBlank()) {
            Integer idFromLocation = locationService.findByLocationName(from);
            Integer idToLocation = locationService.findByLocationName(to);
            List<Route> indirectRoutes = routeService.findIndirectRoutes(idFromLocation, idToLocation);
            model.addAttribute("IndirectRoutes", indirectRoutes);
        }

        model.addAttribute("routes", filteredRoutes);
        model.addAttribute("display", "user/search-routes");

        return "master";
    }

}