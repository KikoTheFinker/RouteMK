package mk.route.routemk.validators;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;

@Component
public class TripValidator {

    /**
     * Validates trip details for saving: free seats and location/ETA consistency.
     */
    public void validateTripData(int freeSeats, List<Integer> locationIds, List<LocalTime> etas) {
        if (freeSeats <= 0) {
            throw new IllegalArgumentException("Free Seats must be greater than zero.");
        }
        if (locationIds == null || etas == null || locationIds.size() != etas.size() || locationIds.contains(null) || etas.contains(null)) {
            throw new IllegalArgumentException("Mismatch between locations and ETAs.");
        }
        LocalTime firstEta = etas.get(0);
        LocalTime lastEta = etas.get(etas.size() - 1);

        for (LocalTime eta : etas) {
            if (eta.isBefore(firstEta) || eta.isAfter(lastEta)) {
                throw new IllegalArgumentException("ETAs must be within the time range of the first and last locations.");
            }
        }
    }
}
