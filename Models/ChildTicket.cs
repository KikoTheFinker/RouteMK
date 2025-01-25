namespace RouteMK.Models;

public class ChildTicket
{
    public int ChildTicketId { get; set; }

    public int TicketId { get; set; }

    public decimal? Discount { get; set; }

    public string Embg { get; set; } = null!;

    public string ParentEmbg { get; set; } = null!;

    public virtual Ticket Ticket { get; set; } = null!;
}