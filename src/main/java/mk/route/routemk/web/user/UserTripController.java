package mk.route.routemk.web.user;

import mk.route.routemk.models.Review;
import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.enums.Status;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trips")
public class UserTripController {
    private final AuthenticationService authenticationService;
    private final TripService tripService;
    private final RouteService routeService;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;

    public UserTripController(AuthenticationService authenticationService,
                              TripService tripService,
                              RouteService routeService,
                              ReviewService reviewService, FavoriteService favoriteService) {
        this.authenticationService = authenticationService;
        this.tripService = tripService;
        this.routeService = routeService;
        this.reviewService = reviewService;
        this.favoriteService = favoriteService;
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

        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        boolean isFavorite = favoriteService.isFavorite(routeId, currentAccountId);
        model.addAttribute("isFavorite", isFavorite);

        model.addAttribute("trips", trips);
        model.addAttribute("cheapestTicketPrice", tripService.getCheapestTicketTableForTrips(trips));
        model.addAttribute("routeSource", route.getSource().getName());
        model.addAttribute("routeId", routeId);
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

    @GetMapping("user/{tripId}")
    public String userTrips(Model model, @PathVariable Integer tripId) {
        Trip trip = tripService.findById(tripId);
        List<Review> reviews = reviewService.findReviewsForTrip(tripId);

        model.addAttribute("trip", trip);
        model.addAttribute("reviews", reviews);
        model.addAttribute("display", "user/details-page");
        model.addAttribute("accountId", authenticationService.getAuthenticatedUserId());
        return "master";
    }

    @PostMapping("/{tripId}/reviews")
    public String addReview(RedirectAttributes redirectAttributes,
                            @PathVariable Integer tripId,
                            @RequestParam("description") String description,
                            @RequestParam(value = "rating") Integer rating) {
        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        try {
            reviewService.addReview(tripId, currentAccountId, description, rating);

            if (rating == null) {
                throw new IllegalArgumentException("Please provide a rating.");
            }

        } catch (Exception exc) {
            redirectAttributes.addFlashAttribute("errorMessage", exc.getMessage());
        }

        return "redirect:/trips/user/" + tripId;
    }

    @PostMapping("{tripId}/removeReview/{reviewId}")
    public String deleteReview(@PathVariable Integer reviewId, @PathVariable Integer tripId) {
        reviewService.deleteById(reviewId);
        return "redirect:/trips/user/" + tripId;
    }

}
