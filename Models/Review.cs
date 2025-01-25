namespace RouteMK.Models;

public class Review
{
    public int ReviewId { get; set; }

    public int AccountId { get; set; }

    public int TicketId { get; set; }

    public string? Description { get; set; }

    public int? Rating { get; set; }

    public virtual Account Account { get; set; } = null!;

    public virtual Ticket Ticket { get; set; } = null!;
}