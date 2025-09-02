package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.views.CompanyPerformanceView;

public interface CompanyPerformanceViewService {

    public CompanyPerformanceView getCompanyPerformanceViewByCompanyName(String companyName);
}
