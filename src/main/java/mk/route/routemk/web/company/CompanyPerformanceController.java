package mk.route.routemk.web.company;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.views.CompanyPerformanceView;
import mk.route.routemk.services.company.interfaces.CompanyPerformanceViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;

@Controller
@RequestMapping("/company-performance")
public class CompanyPerformanceController {

    private final CompanyPerformanceViewService companyPerformanceViewService;

    public CompanyPerformanceController(CompanyPerformanceViewService companyPerformanceViewService) {
        this.companyPerformanceViewService = companyPerformanceViewService;
    }

    @GetMapping
    public String displayCompanyPerformance(Model model, Authentication authentication) {
        Account currentAccount = (Account) authentication.getPrincipal();
        String companyName = null;
        if (currentAccount.getTransportOrganizer() != null) {
            companyName = currentAccount.getTransportOrganizer().getCompanyName();
        }

        CompanyPerformanceView companyPerformanceView =
                companyPerformanceViewService.getCompanyPerformanceViewByCompanyName(companyName);

        System.out.println("CompanyPerformanceView: " + companyPerformanceView.getCompanyName());

        model.addAttribute("statistics", companyPerformanceView);
        model.addAttribute("companyName", companyName);
        model.addAttribute("display", "company/performance");

        return "master";
    }

}