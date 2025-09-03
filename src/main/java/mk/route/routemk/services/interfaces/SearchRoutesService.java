package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.SearchRoutesServiceImpl;

import java.util.List;
import java.util.Map;

/**
 * Service interface for searching and finding routes between locations.
 * Provides functionality to search for direct and indirect routes with pricing information.
 */
public interface SearchRoutesService {

    /**
     * Searches for routes between specified locations.
     *
     * @param from the source location name (can be null to search all sources)
     * @param to   the destination location name (can be null to search all destinations)
     * @return SearchRoutesResult containing direct routes, indirect routes, and pricing maps
     */
    SearchRoutesServiceImpl.SearchRoutesResult search(String from, String to);

    /**
     * Result record containing search results and associated pricing information.
     *
     * @param routes                the list of direct routes found
     * @param indirectRoutes        the list of indirect routes found (routes with transfers)
     * @param cheapestPricePerRoute map of route IDs to their least expensive trip prices
     * @param fastestPricePerRoute  map of route IDs to their fastest trip prices
     */
    record SearchRoutesResult(
            List<Route> routes,
            List<Route> indirectRoutes,
            Map<Integer, Double> cheapestPricePerRoute,
            Map<Integer, Double> fastestPricePerRoute
    ) {
    }
}