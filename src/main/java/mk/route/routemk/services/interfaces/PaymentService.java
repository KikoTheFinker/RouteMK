package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.Payment;
import mk.route.routemk.models.Ticket;

import java.util.Collection;

public interface PaymentService extends GenericService<Payment, Integer> {
    Collection<? extends Ticket> purchaseTickets(Account account, Integer tripPurchasingForId, Integer numTickets);
}
