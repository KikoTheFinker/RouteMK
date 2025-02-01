package mk.route.routemk.services;

import mk.route.routemk.models.Ticket;
import mk.route.routemk.models.Trip;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl extends GenericServiceImpl<Trip, Integer> implements TripService {

    private final TicketService ticketService;

    public TripServiceImpl(GenericRepository<Trip, Integer> genericRepository, TicketService ticketService) {
        super(genericRepository);
        this.ticketService = ticketService;
    }

    @Override
    public List<? extends Trip> findTripsBookedByAccount(Integer accountId) {
        return ticketService.findTripsByAccountId(accountId)
                .stream()
                .map(Ticket::getTrip)
                .sorted(Comparator.comparing(Trip::getDate))
                .collect(Collectors.toList());
    }
}
