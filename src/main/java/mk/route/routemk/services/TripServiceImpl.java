package mk.route.routemk.services;

import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TripRepository;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.specifications.TripSpecification;
import mk.route.routemk.utils.TripComparators;
import mk.route.routemk.utils.TripRankingUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TripServiceImpl extends GenericServiceImpl<Trip, Integer> implements TripService {
    private final TicketService ticketService;
    private final TripRepository tripRepository;

    public TripServiceImpl(GenericRepository<Trip, Integer> genericRepository,
                           TicketService ticketService,
                           TripRepository tripRepository) {
        super(genericRepository);
        this.ticketService = ticketService;
        this.tripRepository = tripRepository;
    }

    @Override
    public List<Trip> findTripsBookedByAccount(Integer accountId) {
        return ticketService.findTicketsByAccountId(accountId).stream()
                .map(Ticket::getTrip)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public List<Trip> findTripsByRouteId(Integer routeId) {
        return findAllByPredicate(TripSpecification.tripsByRoute(routeId));
    }

    @Override
    public Map<Integer, String> getStopNameTableForTrips(Collection<? extends Trip> trips, String from, String to) {
        String fromSafe = Objects.toString(from, "");
        String toSafe = Objects.toString(to, "");
        return trips.stream().collect(Collectors.toMap(
                Trip::getTripId,
                t -> Optional.ofNullable(t.getStops()).orElse(List.of()).stream()
                        .map(s -> s.getLocation().getName())
                        .filter(Objects::nonNull)
                        .filter(n -> !n.equals(fromSafe) && !n.equals(toSafe))
                        .collect(Collectors.joining(", ")),
                (a, b) -> a,
                LinkedHashMap::new
        ));
    }

    @Override
    public Map<Integer, Double> getCheapestTicketTableForTrips(Collection<? extends Trip> trips) {
        Map<Integer, Double> map = new LinkedHashMap<>();
        for (Trip t : trips) {
            int seatsLeft = numTicketsLeftForTrip(t.getTripId());
            map.put(t.getTripId(), (seatsLeft <= 0) ? null : t.getBasePrice());
        }
        return map;
    }


    @Override
    public Map<Integer, Integer> getFreeSeatTableForTrips(Collection<? extends Trip> trips) {
        return trips.stream().collect(Collectors.toMap(
                Trip::getTripId,
                t -> numTicketsLeftForTrip(t.getTripId()),
                (a, b) -> a,
                LinkedHashMap::new
        ));
    }

    @Override
    public Integer numTicketsLeftForTrip(Integer tripId) {
        return tripRepository.findById(tripId).map(Trip::getFreeSeats).orElse(0);
    }

    @Override
    public Optional<Trip> findCheapestTripForRoute(Integer routeId, LocalDate date) {
        return tripsByRouteAndDate(routeId, date)
                .filter(TripRankingUtils::hasSeats)
                .filter(t -> t.getBasePrice() != null)
                .min(TripComparators.CHEAPEST_FIRST);
    }

    @Override
    public Optional<Trip> findFastestTripForRoute(Integer routeId, LocalDate date) {
        return tripsByRouteAndDate(routeId, date)
                .filter(TripRankingUtils::hasSeats)
                .min(TripComparators.FASTEST_FIRST);
    }

    @Override
    public Optional<Trip> findCheapestTripForRoute(Integer routeId) {
        return findCheapestTripForRoute(routeId, null);
    }

    @Override
    public Optional<Trip> findFastestTripForRoute(Integer routeId) {
        return findFastestTripForRoute(routeId, null);
    }


    private Stream<Trip> tripsByRouteAndDate(Integer routeId, LocalDate date) {
        Stream<Trip> base = findAllByPredicate(TripSpecification.tripsByRoute(routeId)).stream();
        return (date == null) ? base : base.filter(t -> Objects.equals(t.getDate(), date));
    }
}
