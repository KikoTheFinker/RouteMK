package mk.route.routemk.services.user;

import mk.route.routemk.models.Ticket;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.TicketRepository;
import mk.route.routemk.services.GenericServiceImpl;
import mk.route.routemk.services.interfaces.TicketService;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TicketServiceImpl extends GenericServiceImpl<Ticket, Integer> implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(GenericRepository<Ticket, Integer> genericRepository, TicketRepository ticketRepository) {
        super(genericRepository);
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Collection<? extends Ticket> findTripsByAccountId(Integer accountId) {
        return ticketRepository.findTicketsByAccount_AccountId(accountId);
    }
}
