package mk.route.routemk.services;

import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Service;

@Service
public class TripServiceImpl extends GenericServiceImpl<Trip, Integer> implements TripService {
    public TripServiceImpl(GenericRepository<Trip, Integer> genericRepository) {
        super(genericRepository);
    }
}
