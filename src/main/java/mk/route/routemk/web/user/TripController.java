package mk.route.routemk.web.user;

import mk.route.routemk.models.Account;
import mk.route.routemk.services.auth.AuthenticationService;
import mk.route.routemk.services.interfaces.TicketService;
import mk.route.routemk.services.interfaces.TripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/trips")
public class TripController {

    private final AuthenticationService authenticationService;
    private final TripService           tripService;

    public TripController(AuthenticationService authenticationService, TripService tripService) {
        this.authenticationService = authenticationService;
        this.tripService = tripService;
    }

    @GetMapping("/user")
    public String myTripsPage(Model model) {

        Optional<Account> accountOpt = authenticationService.getAuthenticatedAccount();

        assert accountOpt.isPresent();

        Integer currentAccountId = accountOpt
                .get()
                .getAccountId();

        model.addAttribute("trips", tripService.findTripsBookedByAccount(currentAccountId));
        model.addAttribute("display", "user/mytrips");

        return "master";
    }
}
