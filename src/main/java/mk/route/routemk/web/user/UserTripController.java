package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.TripStop;
import mk.route.routemk.models.enums.Status;
import mk.route.routemk.services.auth.AuthenticationServiceImpl;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
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

        // filter by upcoming trips
        trips = trips.stream()
                .filter(trip -> trip.getStatus() == Status.NOT_STARTED)
                .collect(Collectors.toList());

        // return empty trips message
        if (trips.isEmpty()) {
            model.addAttribute("emptyMessage", "No upcoming trips.");
        }

        // map for cheapest ticket per trip
        HashMap<Integer, Object> cheapestTicket = new HashMap<>();
        for (Trip trip : trips) {
            Ticket cheapest = ticketService.findCheapestTicketForTrip(trip.getTripId());

            if (trip.getFreeSeats() == 0 || cheapest == null) {
                cheapestTicket.put(trip.getTripId(), "No available tickets.");
            } else {
                cheapestTicket.put(trip.getTripId(), cheapest.getPrice());
            }
        }

        // TODO add stop strings?

        model.addAttribute("trips", trips);
        model.addAttribute("cheapestTicketPrice", cheapestTicket); // used to sort by cheapest ticket price
        model.addAttribute("routeSource", route.getSource().getName());
        model.addAttribute("routeDestination", route.getDestination().getName());
        model.addAttribute("display", "user/view-trips");

        HashMap<Integer, String> stops = new HashMap<>();
        String sourceName = route.getSource().getName();
        String destinationName = route.getDestination().getName();

        for (Trip trip : trips) {
            String stopNames = trip.getStops().stream()
                    .map(stop -> stop.getLocation().getName())
                    .filter(stopName -> !stopName.equals(sourceName) && !stopName.equals(destinationName))
                    .collect(Collectors.joining(", "));
            stops.put(trip.getTripId(), stopNames);
        }

        model.addAttribute("stops", stops);


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
