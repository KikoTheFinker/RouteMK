package mk.route.routemk.web;

import mk.route.routemk.models.Account;
import mk.route.routemk.services.interfaces.GenericService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping({"/", "/home"})
public class WelcomeController {
    private final GenericService<Account, Integer> accountService;

    public WelcomeController(GenericService<Account, Integer> accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String welcome(Model model) {
        System.out.println("TEST");
        List<Account> accountList = accountService.findAll();
        System.out.println(accountList);
        model.addAttribute("accounts", accountService.findAll());
        return "master";
    }
//
//    @GetMapping("/login")
//    public String loginPage() {
//        return "login";
//    }
}
