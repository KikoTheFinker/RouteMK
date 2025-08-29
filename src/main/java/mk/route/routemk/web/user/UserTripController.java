package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.enums.Status;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trips")
public class UserTripController {
    private final AuthenticationService authenticationService;
    private final TripService tripService;
    private final RouteService routeService;
    private final TicketService ticketService;

    public UserTripController(AuthenticationService authenticationService,
                              TripService tripService,
                              RouteService routeService,
                              TicketService ticketService) {
        this.authenticationService = authenticationService;
        this.tripService = tripService;
        this.routeService = routeService;
        this.ticketService = ticketService;
    }

    @GetMapping("/{routeId}")
    public String viewTripsForRoute(@PathVariable Integer routeId, Model model) {
        Route route = routeService.findById(routeId);

        List<Trip> trips = tripService.findTripsByRouteId(routeId);

        trips = trips.stream()
                .filter(trip -> trip.getStatus() == Status.NOT_STARTED)
                .collect(Collectors.toList());

        if (trips.isEmpty()) {
            model.addAttribute("emptyMessage", "No upcoming trips.");
        }

        model.addAttribute("trips", trips);
        model.addAttribute("cheapestTicketPrice", tripService.getCheapestTicketTableForTrips(trips));
        model.addAttribute("routeSource", route.getSource().getName());
        model.addAttribute("routeDestination", route.getDestination().getName());
        model.addAttribute("display", "user/view-trips");

        String sourceName = route.getSource().getName();
        String destinationName = route.getDestination().getName();

        model.addAttribute("stops", tripService.getStopNameTableForTrips(trips, sourceName, destinationName));
        model.addAttribute("display", "user/view-trips");
        model.addAttribute("seatsLeftPerTrip", tripService.getFreeSeatTableForTrips(trips));

        return "master";
    }

    @GetMapping("/user")
    public String myTripsPage(Model model) {
        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        model.addAttribute("trips", tripService.findTripsBookedByAccount(currentAccountId));
        model.addAttribute("display", "user/my-trips");

        return "master";
    }

    @GetMapping("/buy/{id}")
    public String buyTickets(Model model) {
        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        model.addAttribute("trips", tripService.findTripsBookedByAccount(currentAccountId));
        model.addAttribute("display", "user/my-trips");

        return "master";
    }
}
