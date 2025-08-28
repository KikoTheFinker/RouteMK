package mk.route.routemk.specifications;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import mk.route.routemk.models.*;
import org.springframework.data.jpa.domain.Specification;

public class TripSpecification {
    public static Specification<Trip> tripsByRoute(Integer routeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("route").get("routeId"), routeId);
    }


    public static Specification<Trip> findTripsWithStartAndEndLocations(Integer startId, Integer endId) {
        return (root, query, criteriaBuilder) -> {
            Subquery<Integer> startLocationSubquery = query.subquery(Integer.class);
            Root<TripStop> startStopRoot = startLocationSubquery.from(TripStop.class);
            startLocationSubquery.select(startStopRoot.get("trip").get("tripId"))
                    .where(criteriaBuilder.equal(startStopRoot.get("location").get("id"), startId));

            Subquery<Integer> endLocationSubquery = query.subquery(Integer.class);
            Root<TripStop> endStopRoot = endLocationSubquery.from(TripStop.class);
            endLocationSubquery.select(endStopRoot.get("trip").get("tripId"))
                    .where(criteriaBuilder.equal(endStopRoot.get("location").get("id"), endId));

            Join<Trip, TripStop> startTripStop = root.join("stops");
            Join<Trip, TripStop> endTripStop = root.join("stops");

            Join<Trip, Route> routeJoin = root.join("route");
            Join<Route, Location> fromLocationJoin = routeJoin.join("source");
            Join<Route, Location> toLocationJoin = routeJoin.join("destination");
            Join<Route, TransportOrganizer> transportOrgJoin = routeJoin.join("tranOrg");

            Predicate startLocationPredicate = criteriaBuilder.equal(startTripStop.get("location").get("id"), startId);
            Predicate endLocationPredicate = criteriaBuilder.equal(endTripStop.get("location").get("id"), endId);
            Predicate timeOrderPredicate = criteriaBuilder.lessThan(startTripStop.get("stopTime"), endTripStop.get("stopTime"));
            Predicate tripMatchPredicate = criteriaBuilder.equal(startTripStop.get("trip").get("tripId"), endTripStop.get("trip").get("tripId"));
            Predicate excludeDirectRoutePredicate = criteriaBuilder.not(
                    criteriaBuilder.and(
                            criteriaBuilder.equal(routeJoin.get("source").get("id"), startId),
                            criteriaBuilder.equal(routeJoin.get("destination").get("id"), endId)
                    )
            );

            Predicate finalPredicate = criteriaBuilder.and(
                    startLocationPredicate,
                    endLocationPredicate,
                    timeOrderPredicate,
                    tripMatchPredicate,
                    excludeDirectRoutePredicate,
                    criteriaBuilder.in(root.get("tripId")).value(startLocationSubquery),
                    criteriaBuilder.in(root.get("tripId")).value(endLocationSubquery)
            );

            query.distinct(true);

            query.multiselect(
                    root.get("tripId"),
                    root.get("route").get("routeId"),
                    routeJoin.get("source").get("id"),
                    routeJoin.get("destination").get("id"),
                    fromLocationJoin.get("name"),
                    toLocationJoin.get("name"),
                    transportOrgJoin.get("companyName"),
                    root.get("status")
            );

            return finalPredicate;
        };
    }
}
