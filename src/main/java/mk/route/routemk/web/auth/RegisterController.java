package mk.route.routemk.web.auth;

import mk.route.routemk.services.interfaces.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AccountService accountService;

    public RegisterController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String registerPage(@RequestParam(required = false) String error,
                               Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", error);
        }
        model.addAttribute("display", "register");
        return "master";
    }

    @PostMapping
    private String registerUser(@RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam String password,
                                @RequestParam String confirmPassword) {

        try {
            accountService.save(email, name, surname, password, confirmPassword);
        } catch (Exception e) {
            return String.format("redirect:/register?error=%s", e.getMessage());
        }

        return "redirect:/login";
    }

}
