namespace RouteMK.Models;

public class DriverVehicleOperation
{
    public int DriverVehicleOperationId { get; set; }

    public int DriverId { get; set; }

    public int VehicleId { get; set; }

    public virtual Driver Driver { get; set; } = null!;

    public virtual Vehicle Vehicle { get; set; } = null!;
}