namespace RouteMK.Models;

public class Trip
{
    public int TripId { get; set; }

    public int TransportOrganizerId { get; set; }

    public int RouteId { get; set; }

    public int? FreeSeats { get; set; }

    public DateOnly Date { get; set; }

    public virtual ICollection<DriverDrivesOnTrip> DriverDrivesOnTrips { get; set; } = new List<DriverDrivesOnTrip>();

    public virtual Route Route { get; set; } = null!;

    public virtual Account TransportOrganizer { get; set; } = null!;

    public virtual ICollection<TripStop> TripStops { get; set; } = new List<TripStop>();
}