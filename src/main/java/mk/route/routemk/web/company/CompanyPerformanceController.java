package mk.route.routemk.web.company;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.views.CompanyPerformanceView;
import mk.route.routemk.models.views.TopSellingRouteView;
import mk.route.routemk.services.company.interfaces.CompanyPerformanceViewService;
import mk.route.routemk.services.company.interfaces.TopSellingRoutesViewService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;

import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/company-performance")
public class CompanyPerformanceController {

    private final CompanyPerformanceViewService companyPerformanceViewService;
    private final TopSellingRoutesViewService topSellingRoutesViewService;

    public CompanyPerformanceController(CompanyPerformanceViewService companyPerformanceViewService, TopSellingRoutesViewService topSellingRoutesViewService) {
        this.companyPerformanceViewService = companyPerformanceViewService;
        this.topSellingRoutesViewService = topSellingRoutesViewService;
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

        List<TopSellingRouteView> topSellingRoutesView = topSellingRoutesViewService.getTopSellingRoutesForCompany(companyName);

            if (topSellingRoutesView != null && !topSellingRoutesView.isEmpty()) {

            if (topSellingRoutesView.size() > 10) {
                Integer maxPopularity = topSellingRoutesView.stream()
                        .max(Comparator.comparing(TopSellingRouteView::getTotalTicketsSold))
                        .get().getTotalTicketsSold();

                List<TopSellingRouteView> filteredTopSellingRoutes = topSellingRoutesView.stream()
                        .filter(r -> r.getTotalTicketsSold() >= maxPopularity * 0.8).toList();
                model.addAttribute("topSellingRoutesView", filteredTopSellingRoutes);
            } else {
                model.addAttribute("topSellingRoutesView", topSellingRoutesView);
            }

        }

        model.addAttribute("statistics", companyPerformanceView);
        model.addAttribute("companyName", companyName);
        model.addAttribute("display", "company/performance");

        return "master";
    }

}