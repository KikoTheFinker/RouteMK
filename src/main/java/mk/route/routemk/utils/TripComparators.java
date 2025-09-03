package mk.route.routemk.utils;

import mk.route.routemk.models.Trip;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Comparator;

public final class TripComparators {
    private TripComparators() {
    }

    public static final Comparator<Trip> CHEAPEST_FIRST = Comparator
            .comparing((Trip t) -> t.getBasePrice() != null ? t.getBasePrice() : Double.MAX_VALUE)
            .thenComparing(t -> TripRankingUtils.duration(t).orElse(Duration.ofDays(999)))
            .thenComparing(t -> TripRankingUtils.firstDepartureTime(t).orElse(LocalTime.MAX))
            .thenComparing(Trip::getDate, Comparator.nullsLast(Comparator.naturalOrder()));

    public static final Comparator<Trip> FASTEST_FIRST = Comparator
            .comparing((Trip t) -> TripRankingUtils.duration(t).orElse(Duration.ofDays(999)))
            .thenComparing(t -> TripRankingUtils.firstDepartureTime(t).orElse(LocalTime.MAX))
            .thenComparing(Trip::getDate, Comparator.nullsLast(Comparator.naturalOrder()));
}
