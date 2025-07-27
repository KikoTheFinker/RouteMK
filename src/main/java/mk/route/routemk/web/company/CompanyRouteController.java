package mk.route.routemk.web.company;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.company.CompanyAuthorizationServiceImpl;
import mk.route.routemk.services.company.interfaces.CompanyAuthorizationService;
import mk.route.routemk.services.company.interfaces.CompanyRouteService;
import mk.route.routemk.services.interfaces.AccountService;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routes")
public class CompanyRouteController {
    private final CompanyRouteService companyRouteService;
    private final CompanyAuthorizationService companyAuthorizationService;

    public CompanyRouteController(CompanyRouteService companyRouteService, CompanyAuthorizationServiceImpl companyAuthorizationService) {
        this.companyRouteService = companyRouteService;
        this.companyAuthorizationService = companyAuthorizationService;
    }

    @GetMapping("/company")
    public String routes(Model model) {

        model.addAttribute("companyRoutes", companyRouteService.getAuthorizedRoutes());
        model.addAttribute("display", "company/company-route");
        model.addAttribute("companyName", companyAuthorizationService.getAuthenticatedTransportOrganizer().getCompanyName());
        return "master";
    }

    @PostMapping("/company/delete-route/{routeId}")
    public String deleteRoute(@PathVariable Integer routeId) {
        return companyRouteService.deleteRouteIfAuthorized(routeId) ? "redirect:/routes/company" : "redirect:/";
    }
}
