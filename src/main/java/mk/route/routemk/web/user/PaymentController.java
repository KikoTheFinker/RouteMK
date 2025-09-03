package mk.route.routemk.web.user;

import mk.route.routemk.exceptions.PaymentProcessException;
import mk.route.routemk.models.Trip;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.PaymentService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;
    private final TripService tripService;
    private final AuthenticationService authenticationService;

    public PaymentController(PaymentService paymentService, TripService tripService, AuthenticationService authenticationService) {
        this.paymentService = paymentService;
        this.tripService = tripService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/{tripId}")
    public String showPaymentOptionPage(@PathVariable Integer tripId, Model model) {
        Trip trip = tripService.findById(tripId);
        model.addAttribute("trip", trip);
        model.addAttribute("display", "user/ticket-purchase-init");

        return "master";
    }

    @GetMapping("/proceed/{tripId}")
    public String showFinalPaymentPage(@PathVariable Integer tripId, @RequestParam Integer numTickets, Model model) {
        Trip trip = tripService.findById(tripId);

        model.addAttribute("totalPrice", String.format("%.2f", trip.getBasePrice() * numTickets));
        model.addAttribute("trip", trip);
        model.addAttribute("numTickets", numTickets);
        model.addAttribute("display", "user/payment-final");

        return "master";
    }

    @PostMapping("/{tripId}")
    public String pay(@PathVariable Integer tripId, @RequestParam Integer numTickets, RedirectAttributes redirectAttributes) {
        try {
            this.paymentService.purchaseTickets(this.authenticationService.getAuthenticatedAccount().get(), tripId, numTickets);
        } catch (PaymentProcessException exc) {
            redirectAttributes.addFlashAttribute("errorMessage", exc.getMessage());
            return String.format("redirect:/payment/proceed/%d?numTickets=%d", tripId, numTickets);
        }
        return "redirect:/tickets";
    }
}
