namespace RouteMK.Models;

public class Train
{
    public int TrainId { get; set; }

    public int VehicleId { get; set; }

    public virtual Vehicle Vehicle { get; set; } = null!;
}