package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.views.CompanyPerformanceView;

public interface CompanyPerformanceViewRepository extends GenericRepository<CompanyPerformanceView, String> {
    CompanyPerformanceView getCompanyPerformanceViewByCompanyName(String companyName);
}
