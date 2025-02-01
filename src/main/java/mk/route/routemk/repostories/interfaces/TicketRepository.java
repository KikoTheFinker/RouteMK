package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Ticket;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends GenericRepository<Ticket, Integer> {

    List<? extends Ticket> findTicketsByAccount_AccountId(Integer accountId);

}
