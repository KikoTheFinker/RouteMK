package mk.route.routemk.web.user;

import mk.route.routemk.models.Favorite;
import mk.route.routemk.models.Route;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import mk.route.routemk.services.interfaces.FavoriteService;
import mk.route.routemk.services.interfaces.LocationService;
import mk.route.routemk.services.interfaces.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        Integer currentAccountId = authenticationService.getAuthenticatedUserId();
        List<Favorite> favorites = favoriteService.findFavoritesByUserId(currentAccountId);
        List<Route> routes = new ArrayList<>();
        for (Favorite favorite : favorites) {
            routes.add(favorite.getRoute());
        }

        model.addAttribute("favorites", routes);
        model.addAttribute("display", "user/favorites");
        return "master";
    }

    @PostMapping("/change-favorites-status/{routeId}")
    public String changeFavoriteStatus(Model model, @PathVariable Integer routeId,
                                       @RequestParam(value = "remove", required = false, defaultValue = "false") boolean remove) {

        Integer currentAccountId = authenticationService.getAuthenticatedUserId();

        if (remove) {
            favoriteService.removeFavorite(routeId, currentAccountId);
            return "redirect:/favorites";
        }

        if (favoriteService.isFavorite(routeId, currentAccountId)) {
            favoriteService.removeFavorite(routeId, currentAccountId);
        } else {
            favoriteService.addFavorite(routeId, currentAccountId);
        }

        return "redirect:/trips/" + routeId;
    }


}