package mk.route.routemk.web.company;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.models.Trip;
import mk.route.routemk.models.TripStop;
import mk.route.routemk.models.enums.Status;
import mk.route.routemk.services.auth.AuthenticationService;
import mk.route.routemk.services.company.CompanyAuthorizationService;
import mk.route.routemk.services.company.CompanyTripService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.services.interfaces.TripStopService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/routes/company/view-trips")
public class CompanyTripController {
    private final CompanyTripService companyTripService;
    private final LocationService locationService;
    private final RouteService routeService;
    private final TripService tripService;
    private final CompanyAuthorizationService companyAuthorizationService;
    private final TripStopService tripStopService;

    public CompanyTripController(CompanyTripService companyTripService, LocationService locationService, RouteService routeService, TripService tripService, CompanyAuthorizationService companyAuthorizationService, AuthenticationService authenticationService, TripStopService tripStopService) {
        this.companyTripService = companyTripService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.tripService = tripService;
        this.companyAuthorizationService = companyAuthorizationService;
        this.tripStopService = tripStopService;
    }

    @GetMapping("/{routeId}")
    public String routeTrips(@PathVariable Integer routeId, Model model) {
        List<Trip> trips = companyTripService.getAuthorizedTripsByRoute(routeId);

        model.addAttribute("trips", trips);
        model.addAttribute("routeId", routeId);
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("routeSource", routeService.findById(routeId).getSource());
        model.addAttribute("routeDestination", routeService.findById(routeId).getDestination());
        model.addAttribute("display", "/company/company-view-trip");

        return "master";
    }

    @PostMapping("/{routeId}/add-trip")
    public String addNewTrip(@PathVariable("routeId") Integer routeId,
                             @RequestParam("date")
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                             LocalDate date,
                             @RequestParam("freeSeats") int freeSeats,
                             @RequestParam("locations") List<Integer> locationIds,
                             @RequestParam("etas")
                             @DateTimeFormat(pattern = "HH:mm")
                             List<LocalTime> etas,
                             RedirectAttributes redirectAttributes) {

        if (locationIds == null || etas == null || locationIds.size() != etas.size()) {
            redirectAttributes.addFlashAttribute("error", "Mismatch between locations and ETAs.");
            return "redirect:/routes/company/view-trips/";
        }
        Integer transportOrganizerId = companyAuthorizationService.getAuthenticatedTransportOrganizerId();
        if (!companyAuthorizationService.isAuthorizedTransportOrganizer(transportOrganizerId)) {
            return "redirect:/";
        }
        Trip trip = new Trip();
        trip.setDate(date);
        trip.setFreeSeats(freeSeats);
        trip.setTranOrgId(transportOrganizerId);
        trip.setRouteId(routeId);
        trip.setStatus(Status.NOT_STARTED);
        tripService.save(trip);

        for (int i = 0; i < locationIds.size(); i++) {
            TripStop tripStop = new TripStop();
            tripStop.setTripId(trip.getTripId());
            tripStop.setLocationId(locationIds.get(i));
            tripStop.setStopTime(etas.get(i));
            tripStopService.save(tripStop);
        }
        redirectAttributes.addFlashAttribute("message", "Trip created successfully!");
        return "redirect:/routes/company/view-trips";
    }

    @PostMapping("/delete-trip/{tripId}")
    public String deleteTrip(@PathVariable Integer tripId) {
        Integer routeId = tripService.findById(tripId).getRoute().getRouteId();
        return companyTripService.deleteTripIfAuthorized(tripId) ? "redirect:/routes/company/view-trips/" + routeId : "redirect:/";
    }
}
