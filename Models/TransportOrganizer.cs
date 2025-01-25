namespace RouteMK.Models;

public class TransportOrganizer
{
    public int TransportOrganizerId { get; set; }

    public int AccountId { get; set; }

    public string CompanyName { get; set; } = null!;

    public string CompanyEmbg { get; set; } = null!;

    public virtual Account Account { get; set; } = null!;

    public virtual ICollection<Driver> Drivers { get; set; } = new List<Driver>();
}