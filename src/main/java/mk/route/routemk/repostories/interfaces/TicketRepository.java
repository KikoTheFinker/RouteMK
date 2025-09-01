package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Ticket;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TicketRepository extends GenericRepository<Ticket, Integer> {

    Collection<? extends Ticket> findTicketsByAccount_AccountId(Integer accountId);
    List<Ticket> findAllByTrip_TripId(Integer tripId);

    List<Ticket> findTicketsByAccount_AccountIdAndTrip_TripId(Integer accountId, Integer tripId);
}
