namespace RouteMK.Models;

public class Driver
{
    public int DriverId { get; set; }

    public int AccountId { get; set; }

    public int YearsExperience { get; set; }

    public int? TransportOrganizerId { get; set; }

    public virtual Account Account { get; set; } = null!;

    public virtual ICollection<DriverDrivesOnTrip> DriverDrivesOnTrips { get; set; } = new List<DriverDrivesOnTrip>();

    public virtual ICollection<DriverVehicleOperation> DriverVehicleOperations { get; set; } =
        new List<DriverVehicleOperation>();

    public virtual TransportOrganizer? TransportOrganizer { get; set; }
}