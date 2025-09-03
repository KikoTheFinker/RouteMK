package mk.route.routemk.utils;

import mk.route.routemk.models.Trip;
import mk.route.routemk.models.TripStop;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class TripRankingUtils {
    private TripRankingUtils() {}

    public static boolean hasSeats(Trip t) {
        if (t == null) return false;
        int free = t.getFreeSeats();
        return free > 0;
    }

    public static Optional<Duration> duration(Trip t) {
        if (t == null || t.getStops() == null) return Optional.empty();
        List<LocalTime> times = t.getStops().stream()
                .map(TripStop::getStopTime)
                .filter(Objects::nonNull)
                .sorted()
                .toList();
        if (times.size() < 2) return Optional.empty();
        return Optional.of(Duration.between(times.get(0), times.get(times.size() - 1)));
    }

    public static Optional<LocalTime> firstDepartureTime(Trip t) {
        if (t == null || t.getStops() == null) return Optional.empty();
        return t.getStops().stream()
                .map(TripStop::getStopTime)
                .filter(Objects::nonNull)
                .min(LocalTime::compareTo);
    }
}
