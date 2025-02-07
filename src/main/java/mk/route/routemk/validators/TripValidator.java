package mk.route.routemk.validators;

import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
public class TripValidator {

    /**
     * Validates trip details for saving: free seats and location/ETA consistency.
     * @param sourceId The source location ID of the route the trip is undertaking.
     * @param destinationId The destination location ID of the route the trip is undertaking.
     * @param freeSeats Number of free initial/current seats on the trip.
     * @param locationIds Location ids (where the trip stops overall).
     * @param etas ETAs (Estimated Times of Arrival) according to each location.
     */
    public void validateTripData(Integer sourceId, Integer destinationId, int freeSeats, List<Integer> locationIds, List<LocalTime> etas) throws IllegalArgumentException  {

        if (freeSeats <= 0) {
            throw new IllegalArgumentException("Free Seats must be greater than zero.");
        }

        if (locationIds == null || etas == null || locationIds.size() != etas.size() || locationIds.contains(null) || etas.contains(null)) {
            throw new IllegalArgumentException("Data mismatch between locations and ETAs.");
        }

        if (!sourceId.equals(locationIds.get(0)) || !destinationId.equals(locationIds.get(locationIds.size() - 1))) {
            throw new IllegalArgumentException("Mismatch between source and destination.");
        }

        LocalTime srcETA = etas.get(0);
        LocalTime destETA  = etas.get(etas.size() - 1);

        int n = etas.size() - 1;

        // Check validity ETAs of the stops in-between
        for (int i = 1; i < n; ++i) {
            System.out.println(etas.get(i));
            if (
                etas.get(i).isBefore(srcETA)
                    ||
                etas.get(i).isAfter(destETA)
            ) {
                throw new IllegalArgumentException("ETAs must be within the time range of the first and last locations.");
            }
        }
    }
}
