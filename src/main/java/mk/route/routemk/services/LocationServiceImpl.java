package mk.route.routemk.services;

import mk.route.routemk.models.Location;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.LocationRepository;
import mk.route.routemk.services.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LocationServiceImpl extends GenericServiceImpl<Location, Integer> implements LocationService {

    private final LocationRepository locationRepository;

    public LocationServiceImpl(GenericRepository<Location, Integer> genericRepository, LocationRepository locationRepository) {
        super(genericRepository);
        this.locationRepository = locationRepository;
    }

    public Integer findByLocationName(String name) {
        if (name == null || name.isBlank()) {
            return null;
        }

        List<Location> locations = locationRepository.findAll();
        if (locations.isEmpty()) {
            return null;
        }

        Location bestMatch = null;
        int maxLength = -1;
        String inputLower = name.toLowerCase();

        for (Location location : locations) {
            if (location.getName() == null) {
                continue;
            }
            String locationNameLower = location.getName().toLowerCase();

            if (locationNameLower.contains(inputLower)) {
                int currentLength = inputLower.length();
                if (currentLength > maxLength) {
                    maxLength = currentLength;
                    bestMatch = location;
                }
            }
        }

        return bestMatch != null ? bestMatch.getId() : null;
    }
}
