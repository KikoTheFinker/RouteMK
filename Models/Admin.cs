namespace RouteMK.Models;

public class Admin
{
    public int AdminId { get; set; }

    public int AccountId { get; set; }

    public virtual Account Account { get; set; } = null!;
}