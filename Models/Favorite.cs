namespace RouteMK.Models;

public class Favorite
{
    public int FavoriteId { get; set; }

    public int RouteId { get; set; }

    public int AccountId { get; set; }

    public virtual Account Account { get; set; } = null!;

    public virtual Route Route { get; set; } = null!;
}