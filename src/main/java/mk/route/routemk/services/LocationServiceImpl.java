package mk.route.routemk.services;

import mk.route.routemk.models.Location;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.LocationService;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl extends GenericServiceImpl<Location, Integer> implements LocationService {
    public LocationServiceImpl(GenericRepository<Location, Integer> genericRepository) {
        super(genericRepository);
    }
}
