package mk.route.routemk.services;

import mk.route.routemk.exceptions.EntityNotFoundException;
import mk.route.routemk.models.Account;
import mk.route.routemk.models.Favorite;
import mk.route.routemk.models.Location;
import mk.route.routemk.models.Route;
import mk.route.routemk.repostories.interfaces.AccountRepository;
import mk.route.routemk.repostories.interfaces.FavoriteRepository;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.repostories.interfaces.RouteRepository;
import mk.route.routemk.services.interfaces.FavoriteService;
import mk.route.routemk.services.interfaces.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteServiceImpl extends GenericServiceImpl<Favorite, Integer> implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final AccountRepository accountRepository;
    private final RouteRepository routeRepository;

    public FavoriteServiceImpl(GenericRepository<Favorite, Integer> genericRepository, FavoriteRepository favoriteRepository, AccountRepository accountRepository, RouteRepository routeRepository) {
        super(genericRepository);
        this.favoriteRepository = favoriteRepository;
        this.accountRepository = accountRepository;
        this.routeRepository = routeRepository;
    }

    @Override
    public boolean isFavorite(Integer routeId, Integer userId) {
        Favorite exists = favoriteRepository.getFavoritesByAccount_AccountIdAndRoute_RouteId(userId, routeId);
        return exists != null;
    }

    @Override
    public void addFavorite(Integer routeId, Integer userId) {
        Favorite favorite = new Favorite();
        Account account = accountRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: ", userId));
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found: ", routeId));


        favorite.setRoute(route);
        favorite.setAccount(account);
        favoriteRepository.save(favorite);
    }

    @Override
    public void removeFavorite(Integer routeId, Integer userId) {
        Favorite exists = favoriteRepository.getFavoritesByAccount_AccountIdAndRoute_RouteId(userId, routeId);
        if (exists != null) {
            favoriteRepository.delete(exists);
        }
    }

    @Override
    public List<Favorite> findFavoritesByUserId(Integer userId) {
        return favoriteRepository.getFavoritesByAccount_AccountId(userId);
    }


}
