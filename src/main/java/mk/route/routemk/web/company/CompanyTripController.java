package mk.route.routemk.web.company;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.company.CompanyTripService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/routes/company/view-trips")
public class CompanyTripController {
    private final CompanyTripService companyTripService;
    private final LocationService locationService;
    private final RouteService routeService;
    private final TripService tripService;

    public CompanyTripController(CompanyTripService companyTripService, LocationService locationService, RouteService routeService, TripService tripService) {
        this.companyTripService = companyTripService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.tripService = tripService;
    }

    @GetMapping("/{routeId}")
    public String routeTrips(@PathVariable Integer routeId, Model model) {
        List<Trip> trips = companyTripService.getAuthorizedTripsByRoute(routeId);
        Route route = routeService.findById(routeId);

        model.addAttribute("trips", trips);
        model.addAttribute("routeId", routeId);
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("routeSource", route.getSource());
        model.addAttribute("routeDestination", route.getDestination());
        model.addAttribute("display", "/company/company-view-trip");

        return "master";
    }

    @PostMapping("/{routeId}/add-trip")
    public String addNewTrip(@PathVariable("routeId") Integer routeId,
                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @RequestParam("freeSeats") int freeSeats,
                             @RequestParam("locations") List<Integer> locationIds,
                             @RequestParam("etas") @DateTimeFormat(pattern = "HH:mm") List<LocalTime> etas,
                             RedirectAttributes redirectAttributes) {

        try {
            companyTripService.createTrip(routeId, date, freeSeats, locationIds, etas);
            redirectAttributes.addFlashAttribute("message", "Trip created successfully!");
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/routes/company/view-trips/" + routeId;
    }


    @PostMapping("/delete-trip/{tripId}")
    public String deleteTrip(@PathVariable Integer tripId) {
        Integer routeId = tripService.findById(tripId).getRoute().getRouteId();
        return companyTripService.deleteTripIfAuthorized(tripId) ? "redirect:/routes/company/view-trips/" + routeId : "redirect:/";
    }
}
