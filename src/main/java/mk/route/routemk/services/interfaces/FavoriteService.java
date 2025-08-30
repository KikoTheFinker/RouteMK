package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Favorite;

import java.util.List;

public interface FavoriteService {

    boolean isFavorite(Integer routeId, Integer userId);
    void addFavorite(Integer routeId, Integer userId);
    void removeFavorite(Integer routeId, Integer userId);
    List<Favorite> findFavoritesByUserId(Integer userId);

}
