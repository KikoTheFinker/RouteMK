namespace RouteMK.Models;

public class Bus
{
    public int BusId { get; set; }

    public int VehicleId { get; set; }

    public virtual Vehicle Vehicle { get; set; } = null!;
}