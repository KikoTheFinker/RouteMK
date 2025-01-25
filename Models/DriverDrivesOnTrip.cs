namespace RouteMK.Models;

public class DriverDrivesOnTrip
{
    public int DriverDrivesOnTripId { get; set; }

    public int DriverId { get; set; }

    public int TripId { get; set; }

    public virtual Driver Driver { get; set; } = null!;

    public virtual Trip Trip { get; set; } = null!;
}