package mk.route.routemk.web.user;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.Review;
import mk.route.routemk.services.interfaces.ReviewService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("my-reviews")
public class UserReviewController {

    private final ReviewService reviewService;


    public UserReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public String displayReviews(Model model, Authentication authentication) {
        Account currentAccount = (Account) authentication.getPrincipal();
        if (currentAccount == null) {
            return "redirect:/login";
        }

        String email = currentAccount.getEmail();
        List<Review> reviews = reviewService.findByAccountEmail(email);

        model.addAttribute("reviews", reviews);
        model.addAttribute("display", "user/my-reviews");

        return "master";
    }
}
