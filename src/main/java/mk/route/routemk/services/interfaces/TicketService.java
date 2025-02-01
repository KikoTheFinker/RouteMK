package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Ticket;

import java.util.List;

public interface TicketService extends GenericService<Ticket, Integer>{

    List<? extends Ticket> findTripsByAccountId(Integer accountId);

}
