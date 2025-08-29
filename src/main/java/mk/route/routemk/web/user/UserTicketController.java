package mk.route.routemk.web.user;

import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tickets")
public class UserTicketController {

    private final TicketService ticketService;
    private final AuthenticationService authenticationService;

    public UserTicketController(TicketService ticketService, AuthenticationService authenticationService) {
        this.ticketService = ticketService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public String getMyTicketsPage(Model model) {
        model.addAttribute("display", "user/my-tickets");
        model.addAttribute("tickets",
                ticketService.findTicketsByAccountId(this.authenticationService.getAuthenticatedUserId()));
        return "master";
    }
}
