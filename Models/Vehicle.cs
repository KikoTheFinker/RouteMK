namespace RouteMK.Models;

public class Vehicle
{
    public int VehicleId { get; set; }

    public string Model { get; set; } = null!;

    public string Brand { get; set; } = null!;

    public string Capacity { get; set; } = null!;

    public string? YearManufactured { get; set; }

    public virtual Automobile? Automobile { get; set; }

    public virtual Bus? Bus { get; set; }

    public virtual ICollection<DriverVehicleOperation> DriverVehicleOperations { get; set; } =
        new List<DriverVehicleOperation>();

    public virtual Train? Train { get; set; }

    public virtual Van? Van { get; set; }
}