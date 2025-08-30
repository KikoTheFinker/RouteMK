package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Ticket;

import java.util.Collection;

public interface TicketService extends GenericService<Ticket, Integer>{

    Collection<? extends Ticket> findTicketsByAccountId(Integer accountId);
    Ticket findCheapestTicketForTrip(Integer tripId);
    Collection<? extends Ticket> findAllTicketsForTrip(Integer tripId);
}
