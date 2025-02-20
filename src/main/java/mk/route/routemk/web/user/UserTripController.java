package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.auth.AuthenticationServiceImpl;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trips")
public class UserTripController {
    private final AuthenticationService authenticationService;
    private final TripService tripService;
    private final RouteService routeService;

    public UserTripController(AuthenticationService authenticationService, TripService tripService, RouteService routeService) {
        this.authenticationService = authenticationService;
        this.tripService = tripService;
        this.routeService = routeService;
    }

    @GetMapping("/{routeId}")
    public String viewTripsForRoute(@PathVariable Integer routeId, Model model) {
        Route route = routeService.findById(routeId);

        model.addAttribute("trips", tripService.findTripsByRouteId(routeId));
        model.addAttribute("routeSource", route.getSource().getName());
        model.addAttribute("routeDestination", route.getDestination().getName());

        model.addAttribute("display", "user/view-trips");
        return "master";
    }

    @GetMapping("/user")
    public String myTripsPage(Model model) {
        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        model.addAttribute("trips", tripService.findTripsBookedByAccount(currentAccountId));
        model.addAttribute("display", "user/my-trips");

        return "master";
    }
}
