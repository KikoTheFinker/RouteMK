package mk.route.routemk.services;

import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TripRepository;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import mk.route.routemk.specifications.TripSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return ticketService.findTicketsByAccountId(accountId).stream().map(Ticket::getTrip).toList();
    }

    @Override
    public List<Trip> findTripsByRouteId(Integer routeId) {
        return findAllByPredicate(TripSpecification.tripsByRoute(routeId));
    }

    @Override
    public Map<Integer, String> getStopNameTableForTrips(Collection<? extends Trip> trips, String from, String to) {
        return trips.stream().collect(Collectors.toMap(
                Trip::getTripId,
                t -> t.getStops().stream()
                        .map(s -> s.getLocation().getName())
                        .filter(n -> !n.equals(from) && !n.equals(to))
                        .collect(Collectors.joining(", "))
        ));
    }

    @Override
    public Map<Integer, Object> getCheapestTicketTableForTrips(Collection<? extends Trip> trips) {
        return trips.stream().collect(Collectors.toMap(
                Trip::getTripId,
                t -> {
                    int seatsLeft = numTicketsLeftForTrip(t.getTripId());
                    if (seatsLeft == 0) return "No available seats.";
                    return t.getBasePrice() != null ? t.getBasePrice() : "Price unavailable";
                }
        ));
    }

    @Override
    public Map<Integer, Integer> getFreeSeatTableForTrips(Collection<? extends Trip> trips) {
        return trips.stream().collect(Collectors.toMap(
                Trip::getTripId,
                t -> numTicketsLeftForTrip(t.getTripId())
        ));
    }

    @Override
    public Integer numTicketsLeftForTrip(Integer tripId) {
        return tripRepository.findById(tripId).map(Trip::getFreeSeats).orElse(0);
    }

    @Override
    public List<Trip> findIndirectTrips(Integer startId, Integer endId) {
        Specification<Trip> spec = TripSpecification.findTripsWithStartAndEndLocations(startId, endId);
        return findAllByPredicate(spec);
    }
}
