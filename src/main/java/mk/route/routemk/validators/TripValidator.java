package mk.route.routemk.validators;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
public class TripValidator {

    /**
     * Validates trip details for saving: free seats and location/ETA consistency.
     */
    public void validateTripData(Integer sourceId, Integer destinationId, int freeSeats, List<Integer> locationIds, List<LocalTime> etas) {
        if (freeSeats <= 0) {
            throw new IllegalArgumentException("Free Seats must be greater than zero.");
        }
        if (locationIds == null || etas == null || locationIds.size() != etas.size() || locationIds.contains(null) || etas.contains(null)) {
            throw new IllegalArgumentException("Mismatch between locations and ETAs.");
        }

        if (!sourceId.equals(locationIds.get(0)) || !destinationId.equals(locationIds.get(locationIds.size() - 1))) {
            throw new IllegalArgumentException("Mismatch between source and destination.");
        }

        for (LocalTime eta : etas) {
            if (eta.isBefore(etas.get(0)) || eta.isAfter(etas.get(etas.size() - 1))) {
                throw new IllegalArgumentException("ETAs must be within the time range of the first and last locations.");
            }
        }
    }
}
