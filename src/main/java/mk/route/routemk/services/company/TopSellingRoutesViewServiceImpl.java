package mk.route.routemk.services.company;

import mk.route.routemk.models.views.TopSellingRouteView;
import mk.route.routemk.repostories.interfaces.TopSellingRoutesViewRepository;
import mk.route.routemk.services.company.interfaces.TopSellingRoutesViewService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopSellingRoutesViewServiceImpl implements TopSellingRoutesViewService {
    private final TopSellingRoutesViewRepository topSellingRoutesViewRepository;

    public TopSellingRoutesViewServiceImpl(TopSellingRoutesViewRepository topSellingRoutesViewRepository) {
        this.topSellingRoutesViewRepository = topSellingRoutesViewRepository;
    }

    @Override
    public List<TopSellingRouteView> getTopSellingRoutesForCompany(String company) {
       return topSellingRoutesViewRepository.getAllByTransportOrganizerName(company);
    }
}
