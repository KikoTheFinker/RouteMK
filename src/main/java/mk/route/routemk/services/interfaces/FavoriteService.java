package mk.route.routemk.services.interfaces;

public interface FavoriteService {

    boolean isFavorite(Integer routeId, Integer userId);
    void addFavorite(Integer routeId, Integer userId);
    void removeFavorite(Integer routeId, Integer userId);

}
