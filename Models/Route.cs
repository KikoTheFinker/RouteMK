namespace RouteMK.Models;

public class Route
{
    public int RouteId { get; set; }

    public int TransportOrganizerId { get; set; }

    public int FromLocationId { get; set; }

    public int ToLocationId { get; set; }

    public virtual ICollection<Favorite> Favorites { get; set; } = new List<Favorite>();

    public virtual Location FromLocation { get; set; } = null!;

    public virtual Location ToLocation { get; set; } = null!;

    public virtual Account TransportOrganizer { get; set; } = null!;

    public virtual ICollection<TripDaysActive> TripDaysActives { get; set; } = new List<TripDaysActive>();

    public virtual ICollection<Trip> Trips { get; set; } = new List<Trip>();
}