package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search-routes")
public class UserRouteController {
    private final RouteService routeService;
    private final TripService tripService;
    private final LocationService locationService;

    public UserRouteController(RouteService routeService, TripService tripService, LocationService locationService) {
        this.routeService = routeService;
        this.tripService = tripService;
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
            Integer startLocationId = locationService.findByLocationName(from);
            Integer endLocationId = locationService.findByLocationName(to);
            List<Trip> trips = tripService.findIndirectTrips(startLocationId, endLocationId);
            List<Route> indirectRoutes = trips.stream()
                    .map(Trip::getRoute)
                    .distinct()
                    .collect(Collectors.toList());
            model.addAttribute("IndirectRoutes", indirectRoutes);
        }


        model.addAttribute(filteredRoutes.isEmpty() ? "noRoutesMessage" : "routes",
                filteredRoutes.isEmpty() ? "No routes found for your search." : filteredRoutes);
        model.addAttribute("display", "user/search-routes");

        return "master";
    }

}