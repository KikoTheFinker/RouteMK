package mk.route.routemk.web.company;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.company.interfaces.CompanyTripService;
import mk.route.routemk.services.interfaces.DriverService;
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
@RequestMapping("/routes/company/view-trips/{routeId}")
public class CompanyTripController {
    private final CompanyTripService companyTripService;
    private final LocationService locationService;
    private final RouteService routeService;
    private final TripService tripService;
    private final DriverService driverService;

    public CompanyTripController(CompanyTripService companyTripService,
                                 LocationService locationService,
                                 RouteService routeService,
                                 TripService tripService,
                                 DriverService driverService) {
        this.companyTripService = companyTripService;
        this.locationService = locationService;
        this.routeService = routeService;
        this.tripService = tripService;
        this.driverService = driverService;
    }

    @GetMapping
    public String routeTrips(@PathVariable Integer routeId, Model model) {
        Route route = routeService.findById(routeId);

        if (route == null) {
            return "redirect:/login";
        }

        List<Trip> tripsAuthorized = companyTripService.getAuthorizedTripsByRoute(routeId);
        var drivers = driverService.findByTranOrg(route.getTranOrg());

        model.addAttribute("trips", tripsAuthorized);
        model.addAttribute("routeId", routeId);
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("freeSeatMap", tripService.getFreeSeatTableForTrips(tripsAuthorized));
        model.addAttribute("routeSource", route.getSource());
        model.addAttribute("routeDestination", route.getDestination());
        model.addAttribute("drivers", drivers);
        model.addAttribute("display", "company/company-view-trip");

        return "master";
    }

    @PostMapping("/assign-driver/{tripId}")
    public String assignDriver(@PathVariable Integer routeId,
                               @PathVariable Integer tripId,
                               @RequestParam("driverId") Integer driverId,
                               RedirectAttributes redirectAttributes) {
        try {
            companyTripService.assignDriverIfAuthorized(tripId, driverId);
            redirectAttributes.addFlashAttribute("message", "Driver assigned successfully.");
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/routes/company/view-trips/" + routeId;
    }

    @PostMapping("/unassign-driver/{tripId}")
    public String unassignDriver(@PathVariable Integer routeId,
                                 @PathVariable Integer tripId,
                                 @RequestParam("driverId") Integer driverId,
                                 RedirectAttributes redirectAttributes) {
        try {
            companyTripService.unassignDriverIfAuthorized(tripId, driverId);
            redirectAttributes.addFlashAttribute("message", "Driver unassigned.");
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/routes/company/view-trips/" + routeId;
    }

    @PostMapping("/add-trip")
    public String addNewTrip(@PathVariable Integer routeId,
                             @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                             @RequestParam("freeSeats") int freeSeats,
                             @RequestParam("basePrice") double basePrice,
                             @RequestParam("locations") List<Integer> locationIds,
                             @RequestParam("etas") @DateTimeFormat(pattern = "HH:mm") List<LocalTime> etas,
                             RedirectAttributes redirectAttributes) {
        try {
            Route route = routeService.findById(routeId);
            companyTripService.createTrip(route, date, freeSeats, basePrice, locationIds, etas);
            redirectAttributes.addFlashAttribute("message", "Trip created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/routes/company/view-trips/" + routeId;
    }

    @PostMapping("/edit-trip/{tripId}")
    public String editTrip(@PathVariable Integer routeId,
                           @PathVariable Integer tripId,
                           @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                           @RequestParam("freeSeats") int freeSeats,
                           @RequestParam("basePrice") double basePrice,
                           @RequestParam("locations") List<Integer> locationIds,
                           @RequestParam("etas") @DateTimeFormat(pattern = "HH:mm") List<LocalTime> etas,
                           RedirectAttributes redirectAttributes) {
        try {
            Route route = routeService.findById(routeId);
            companyTripService.updateTrip(route, tripId, date, freeSeats, basePrice, locationIds, etas);
            redirectAttributes.addFlashAttribute("message", "Trip updated successfully!");
        } catch (IllegalArgumentException | SecurityException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/routes/company/view-trips/" + routeId;
    }


    @PostMapping("/delete-trip/{tripId}")
    public String deleteTrip(@PathVariable Integer routeId, @PathVariable Integer tripId) {
        return companyTripService.deleteTripIfAuthorized(tripId)
                ? "redirect:/routes/company/view-trips/" + routeId
                : "redirect:/";
    }

}
