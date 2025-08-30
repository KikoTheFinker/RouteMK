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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl extends GenericServiceImpl<Trip, Integer> implements TripService {

    private final TicketService ticketService;
    private final TripRepository tripRepository;

    public TripServiceImpl(GenericRepository<Trip, Integer> genericRepository, TicketService ticketService, TripRepository tripService) {
        super(genericRepository);
        this.ticketService = ticketService;
        this.tripRepository = tripService;
    }

    @Override
    public List<Trip> findTripsBookedByAccount(Integer accountId) {
        return ticketService.findTicketsByAccountId(accountId)
                .stream()
                .map(Ticket::getTrip)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trip> findTripsByRouteId(Integer routeId) {
        return findAllByPredicate(TripSpecification.tripsByRoute(routeId));
    }

    @Override
    public Map<Integer, String> getStopNameTableForTrips(Collection<? extends Trip> trips, String from, String to) {
        Map<Integer, String> stops = new HashMap<>();

        for (Trip trip : trips) {
            String stopNames = trip.getStops().stream()
                    .map(stop -> stop.getLocation().getName())
                    .filter(stopName -> !stopName.equals(from) && !stopName.equals(to))
                    .collect(Collectors.joining(", "));
            stops.put(trip.getTripId(), stopNames);
        }

        return stops;
    }

    @Override
    public Map<Integer, Object> getCheapestTicketTableForTrips(Collection<? extends Trip> trips) {
        Map<Integer, Object> cheapestTicketPerTrip = new HashMap<>();

        for (Trip trip : trips) {
            Integer tripId = trip.getTripId();

            Ticket cheapest = ticketService.findCheapestTicketForTrip(tripId);

            if (numTicketsLeftForTrip(trip.getTripId()) == 0 || cheapest == null) {
                cheapestTicketPerTrip.put(tripId, "No available tickets.");
            } else {
                cheapestTicketPerTrip.put(tripId, cheapest.getPrice());
            }
        }
        return cheapestTicketPerTrip;
    }

    @Override
    public Map<Integer, Integer> getFreeSeatTableForTrips(Collection<? extends Trip> trips) {
        Map<Integer, Integer> freeSeatTable = new HashMap<>();

        for (Trip trip : trips) {
            Integer tripId = trip.getTripId();
            Integer seatsLeft = numTicketsLeftForTrip(trip.getTripId());

            freeSeatTable.put(tripId, seatsLeft);
        }

        return freeSeatTable;
    }

    @Override
    public Integer numTicketsLeftForTrip(Integer tripId) {
        int tripSeatCapacity = tripRepository.findById(tripId).get().getSeatCapacity();
        int ticketsPurchasedAlready = ticketService.findAllTicketsForTrip(tripId).size();

        return tripSeatCapacity - ticketsPurchasedAlready;
    }

    public List<Trip> findIndirectTrips(Integer startId, Integer endId) {
        Specification<Trip> trips = TripSpecification.findTripsWithStartAndEndLocations(startId, endId);
        System.out.printf("Trips with start and end locations: %s\n", trips);
        return findAllByPredicate(trips);
    }

}