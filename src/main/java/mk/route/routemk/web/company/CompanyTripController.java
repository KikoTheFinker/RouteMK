package mk.route.routemk.web.company;

import mk.route.routemk.models.Location;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.company.CompanyTripService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripStopService;
import mk.route.routemk.specifications.TripStopSpecification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/routes/company/view-trips")
public class CompanyTripController {
    private final CompanyTripService companyTripService;
    private final LocationService locationService;
    private final RouteService routeService;
    private final TripStopService tripStopService;

    public CompanyTripController(CompanyTripService companyTripService, LocationService locationService, RouteService routeService, TripStopService tripStopService) {
        this.companyTripService = companyTripService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.tripStopService = tripStopService;
    }

    @GetMapping("/{routeId}")
    public String routeTrips(@PathVariable Integer routeId, Model model) {
        List<Trip> trips = companyTripService.getAuthorizedTripsByRoute(routeId);
        List<Integer> tripIds = trips.isEmpty() ? List.of() : trips.stream().map(Trip::getTripId).toList();

        model.addAttribute("trips", trips);
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("tripStops", tripStopService.findAllByPredicate(TripStopSpecification.tripStopsByTripIds(tripIds)));
        model.addAttribute("routeSource", routeService.findById(routeId).getSource());
        model.addAttribute("routeDestination", routeService.findById(routeId).getDestination());
        model.addAttribute("display", "/company/company-view-trip");

        return "master";
    }
}
