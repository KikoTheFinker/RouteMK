package mk.route.routemk.services.company;

import mk.route.routemk.models.views.CompanyPerformanceView;
import mk.route.routemk.repostories.interfaces.CompanyPerformanceViewRepository;
import mk.route.routemk.services.company.interfaces.CompanyPerformanceViewService;
import org.springframework.stereotype.Service;

@Service
public class CompanyPerformanceViewServiceImpl implements CompanyPerformanceViewService {

    private final CompanyPerformanceViewRepository companyPerformanceViewRepository;

    public CompanyPerformanceViewServiceImpl(CompanyPerformanceViewRepository companyPerformanceViewRepository) {
        this.companyPerformanceViewRepository = companyPerformanceViewRepository;
    }

    @Override
    public CompanyPerformanceView getCompanyPerformanceViewByCompanyName(String companyName) {
        return companyPerformanceViewRepository.getCompanyPerformanceViewByCompanyName(companyName);
    }
}
