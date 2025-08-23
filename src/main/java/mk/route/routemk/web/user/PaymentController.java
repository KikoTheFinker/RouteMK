package mk.route.routemk.web.user;

import mk.route.routemk.services.interfaces.PaymentService;
import mk.route.routemk.services.interfaces.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
// TODO: implement payment processing
public class PaymentController {
    private final PaymentService paymentService;
    private final TicketService ticketService;

    public PaymentController(PaymentService paymentService, TicketService ticketService) {
        this.paymentService = paymentService;
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showPaymentOptionPage(Model model) {
        return "kure";
    }
}
