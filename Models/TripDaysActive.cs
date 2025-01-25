namespace RouteMK.Models;

public class TripDaysActive
{
    public int TripDaysActiveId { get; set; }

    public int RouteId { get; set; }

    public virtual Route Route { get; set; } = null!;
}