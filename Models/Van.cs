namespace RouteMK.Models;

public class Van
{
    public int VanId { get; set; }

    public int VehicleId { get; set; }

    public virtual Vehicle Vehicle { get; set; } = null!;
}