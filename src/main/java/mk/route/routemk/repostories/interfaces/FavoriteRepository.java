package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Favorite;

import java.util.List;

public interface FavoriteRepository extends GenericRepository<Favorite, Integer>{

    Favorite getFavoritesByAccount_AccountIdAndRoute_RouteId(Integer accountId, Integer routeId);
    List<Favorite> getFavoritesByAccount_AccountId(Integer routeId);
}
