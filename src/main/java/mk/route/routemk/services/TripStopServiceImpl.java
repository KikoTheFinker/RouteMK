package mk.route.routemk.services;

import mk.route.routemk.models.TripStop;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.TripStopService;
import org.springframework.stereotype.Service;

@Service
public class TripStopServiceImpl extends GenericServiceImpl<TripStop, Integer> implements TripStopService {
    public TripStopServiceImpl(GenericRepository<TripStop, Integer> genericRepository) {
        super(genericRepository);
    }
}
