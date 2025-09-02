package mk.route.routemk.services.company.interfaces;

import mk.route.routemk.models.views.TopSellingRouteView;

import java.util.List;

public interface TopSellingRoutesViewService {
    List<TopSellingRouteView> getTopSellingRoutesForCompany(String company);
}
