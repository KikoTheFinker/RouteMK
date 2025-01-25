namespace RouteMK.Models;

public class Location
{
    public int LocationId { get; set; }

    public decimal Latitude { get; set; }

    public decimal Longitude { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Route> RouteFromLocations { get; set; } = new List<Route>();

    public virtual ICollection<Route> RouteToLocations { get; set; } = new List<Route>();

    public virtual ICollection<Ticket> TicketGetsOffLocations { get; set; } = new List<Ticket>();

    public virtual ICollection<Ticket> TicketGetsOnLocations { get; set; } = new List<Ticket>();

    public virtual ICollection<TripStop> TripStops { get; set; } = new List<TripStop>();
}