package mk.route.routemk.web.user;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.auth.AuthenticationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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
    public String viewTripsForRoute(Model model, @PathVariable Integer routeId) {
        List<Trip> trips = tripService.findTripsByRouteId(routeId);
        trips.sort(Comparator.comparing(Trip::getDate).reversed());
        Route route = routeService.findById(routeId);
        model.addAttribute("display", "user/view-trips");
        model.addAttribute("trips", trips);
        model.addAttribute("from", route.getSource().getName());
        model.addAttribute("to", route.getDestination().getName());
        return "master";
    }

    @GetMapping("/user")
    public String myTripsPage(Model model) {

        Optional<Account> accountOpt = authenticationService.getAuthenticatedAccount();

        assert accountOpt.isPresent();

        Integer currentAccountId = accountOpt
                .get()
                .getAccountId();

        model.addAttribute("trips", tripService.findTripsBookedByAccount(currentAccountId));
        model.addAttribute("display", "user/my-trips");

        return "master";
    }
}
