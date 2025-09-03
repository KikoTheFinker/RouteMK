package mk.route.routemk.services;

import mk.route.routemk.exceptions.PaymentProcessException;
import mk.route.routemk.models.*;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.PaymentRepository;
import mk.route.routemk.services.interfaces.PaymentService;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer> implements PaymentService {

    private final TripService tripService;
    private final TicketService ticketService;
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(GenericRepository<Payment, Integer> genericRepository, TripService tripService, TicketService ticketService, PaymentRepository paymentRepository) {
        super(genericRepository);
        this.tripService = tripService;
        this.ticketService = ticketService;
        this.paymentRepository = paymentRepository;
    }

    @Override
    @Transactional
    public Collection<? extends Ticket> purchaseTickets(Account account, Integer tripPurchasingForId, Integer numTickets) {
        Trip tripPurchasingFor = tripService.findById(tripPurchasingForId);
        Integer numSeatsAvailable = tripService.numTicketsLeftForTrip(tripPurchasingForId);

        if (numSeatsAvailable < numTickets) {
            throw new PaymentProcessException(String.format("This trip does not have enough capacity to book %d tickets, only %d tickets available", numTickets, numSeatsAvailable));
        }

        Payment payment = new Payment(account, numTickets * tripPurchasingFor.getBasePrice(), numTickets);
        payment = paymentRepository.save(payment);
        Route routeOfTrip = tripPurchasingFor.getRoute();

        List<Ticket> ticketsBought = new ArrayList<>();

        int seatStartIterate = tripPurchasingFor.getSeatCapacity() - numSeatsAvailable;
        for (int i = 0; i < numTickets; i++) {
            Ticket purchased = new Ticket(tripPurchasingFor,
                    routeOfTrip.getSource(),
                    routeOfTrip.getDestination(),
                    account,
                    payment,
                    payment.getPaymentDate(),
                    LocalTime.now(),
                    tripPurchasingFor.getBasePrice(),
                    Integer.toString(++seatStartIterate));

            ticketsBought.add(
                    ticketService.save(purchased)
            );
        }

        return ticketsBought;
    }
}
