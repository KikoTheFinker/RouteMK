package mk.route.routemk.web.user;

import mk.route.routemk.models.Route;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.FavoriteService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/favorites")
public class UserFavoritesController {
    private final AuthenticationService authenticationService;
    private final FavoriteService favoriteService;

    public UserFavoritesController(AuthenticationService authenticationService, FavoriteService favoriteService) {
        this.authenticationService = authenticationService;
        this.favoriteService = favoriteService;
    }


    @GetMapping
    public String displayFavorites(Model model) {

        model.addAttribute("display", "user/favorites");
        return "master";
    }

    @GetMapping("/change-favorites-status")
    public String changeFavoriteStatus(@RequestParam("routeId") Integer routeId) {

        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        if (favoriteService.isFavorite(routeId, currentAccountId)) {
            favoriteService.removeFavorite(routeId, currentAccountId);
        } else {
            favoriteService.addFavorite(routeId, currentAccountId);
        }

        System.out.println(favoriteService.isFavorite(routeId, currentAccountId));

        return "redirect:/trips/" + routeId;
    }


}