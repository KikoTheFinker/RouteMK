package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Favorite;

public interface FavoriteRepository extends GenericRepository<Favorite, Integer>{

    Favorite getFavoritesByAccount_AccountIdAndRoute_RouteId(Integer accountId, Integer routeId);
}
