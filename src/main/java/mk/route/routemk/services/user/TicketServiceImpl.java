package mk.route.routemk.services.user;

import mk.route.routemk.models.Ticket;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TicketRepository;
import mk.route.routemk.services.GenericServiceImpl;
import mk.route.routemk.services.interfaces.TicketService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Comparator;

@Service
public class TicketServiceImpl extends GenericServiceImpl<Ticket, Integer> implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(GenericRepository<Ticket, Integer> genericRepository, TicketRepository ticketRepository) {
        super(genericRepository);
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Collection<? extends Ticket> findTicketsByAccountId(Integer accountId) {
        return ticketRepository
                .findTicketsByAccount_AccountId(accountId)
                .stream()
                .sorted(Comparator.comparing(Ticket::getDatePurchased).reversed())
                .toList();
    }

    @Override
    public Ticket findCheapestTicketForTrip(Integer tripId) {
        return ticketRepository.findAllByTrip_TripId(tripId)
                .stream()
                .min(Comparator.comparing(Ticket::getPrice))
                .orElse(null);
    }

    @Override
    public Collection<? extends Ticket> findAllTicketsForTrip(Integer tripId) {
        return ticketRepository.findAllByTrip_TripId(tripId);
    }
}
