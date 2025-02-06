package mk.route.routemk.services;

import mk.route.routemk.models.TripStop;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TripStopRepository;
import mk.route.routemk.services.interfaces.TripStopService;
import org.springframework.stereotype.Service;

@Service
public class TripStopServiceImpl extends GenericServiceImpl<TripStop, Integer> implements TripStopService {
    private final TripStopRepository tripStopRepository;

    public TripStopServiceImpl(TripStopRepository tripStopRepository) {
        super(tripStopRepository);
        this.tripStopRepository = tripStopRepository;
    }

    /**
     * Deletes trip stops for given tripId.
     * @param tripId the tripId
     */
    @Override
    public void deleteByTripId(Integer tripId) {
        tripStopRepository.deleteByTripId(tripId);
    }
}
