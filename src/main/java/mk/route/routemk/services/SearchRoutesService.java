package mk.route.routemk.services;

import mk.route.routemk.models.Route;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class SearchRoutesService {

    private final RouteService routeService;
    private final LocationService locationService;
    private final TripService tripService;

    public SearchRoutesService(RouteService routeService,
                               LocationService locationService,
                               TripService tripService) {
        this.routeService = routeService;
        this.locationService = locationService;
        this.tripService = tripService;
    }

    public SearchRoutesResult search(String from, String to) {
        from = normalize(from);
        to = normalize(to);

        List<Route> routes = Optional.ofNullable(
                (from == null && to == null)
                        ? routeService.findAll()
                        : routeService.findRouteByFromAndToDest(from, to)
        ).orElse(List.of());

        routes = routes.stream()
                .filter(Objects::nonNull)
                .filter(r -> r.getSource() != null)
                .filter(r -> r.getDestination() != null)
                .filter(r -> r.getTranOrg() != null)
                .toList();

        List<Route> indirectRoutes = List.of();
        if (from != null && to != null) {
            Integer fromId = locationService.findByLocationName(from);
            Integer toId = locationService.findByLocationName(to);
            if (fromId != null && toId != null) {
                indirectRoutes = Optional.ofNullable(
                        routeService.findIndirectRoutes(fromId, toId)
                ).orElse(List.of());
            }
        }

        Map<Integer, Double> cheapest = buildPriceMap(routes, tripService::findCheapestTripForRoute);
        Map<Integer, Double> fastest = buildPriceMap(routes, tripService::findFastestTripForRoute);

        return new SearchRoutesResult(routes, indirectRoutes, cheapest, fastest);
    }

    private String normalize(String val) {
        return (val == null || val.isBlank()) ? null : val.trim();
    }

    private Map<Integer, Double> buildPriceMap(
            List<Route> routes,
            Function<Integer, Optional<Trip>> tripFinder) {

        Map<Integer, Double> map = new LinkedHashMap<>();
        for (Route r : routes) {
            if (r != null && r.getRouteId() != null) {
                Double price = tripFinder.apply(r.getRouteId())
                        .map(Trip::getBasePrice)
                        .orElse(null);
                map.put(r.getRouteId(), price);
            }
        }
        return map;
    }

    public record SearchRoutesResult(
            List<Route> routes,
            List<Route> indirectRoutes,
            Map<Integer, Double> cheapestPricePerRoute,
            Map<Integer, Double> fastestPricePerRoute
    ) {
    }
}
