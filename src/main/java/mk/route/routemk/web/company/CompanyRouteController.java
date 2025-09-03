package mk.route.routemk.web.company;

import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import mk.route.routemk.services.company.interfaces.CompanyRouteService;
import mk.route.routemk.services.interfaces.LocationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/routes")
public class CompanyRouteController {
    private final CompanyRouteService companyRouteService;
    private final CompanyAuthorizationService companyAuthorizationService; // if still used for the name
    private final LocationService locationService;

    public CompanyRouteController(CompanyRouteService companyRouteService,
                                  CompanyAuthorizationService companyAuthorizationService,
                                  LocationService locationService) {
        this.companyRouteService = companyRouteService;
        this.companyAuthorizationService = companyAuthorizationService;
        this.locationService = locationService;
    }

    @GetMapping("/company")
    public String routes(Model model) {
        model.addAttribute("companyRoutes", companyRouteService.getAuthorizedRoutes());
        model.addAttribute("locations", locationService.findAll());
        model.addAttribute("display", "company/company-route");
        model.addAttribute("companyName",
                companyAuthorizationService.getAuthenticatedTransportOrganizer().getCompanyName());
        return "master";
    }

    @PostMapping("/company/add-route")
    public String addRoute(@RequestParam Integer sourceId,
                           @RequestParam Integer destinationId,
                           RedirectAttributes ra) {
        try {
            companyRouteService.createRouteForAuthenticatedOrganizer(sourceId, destinationId);
            ra.addFlashAttribute("successMessage", "Route created successfully.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            ra.addFlashAttribute("errorMessage", ex.getMessage());
            ra.addFlashAttribute("openAddModal", true);
        } catch (Exception ex) {
            ra.addFlashAttribute("errorMessage", "Something went wrong. Please try again.");
            ra.addFlashAttribute("openAddModal", true);
        }
        return "redirect:/routes/company";
    }

    @PostMapping("/company/delete-route/{routeId}")
    public String deleteRoute(@PathVariable Integer routeId, RedirectAttributes ra) {
        if (companyRouteService.deleteRouteIfAuthorized(routeId)) {
            ra.addFlashAttribute("successMessage", "Route deleted.");
        } else {
            ra.addFlashAttribute("errorMessage", "Not allowed to delete this route.");
        }
        return "redirect:/routes/company";
    }

}
