namespace RouteMK.Models;

public class TripStop
{
    public int TripStopId { get; set; }

    public int TripId { get; set; }

    public int LocationId { get; set; }

    public TimeOnly StopTime { get; set; }

    public virtual Location Location { get; set; } = null!;

    public virtual Trip Trip { get; set; } = null!;
}