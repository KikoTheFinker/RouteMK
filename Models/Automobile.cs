namespace RouteMK.Models;

public class Automobile
{
    public int AutomobileId { get; set; }

    public int VehicleId { get; set; }

    public virtual Vehicle Vehicle { get; set; } = null!;
}