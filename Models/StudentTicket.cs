namespace RouteMK.Models;

public class StudentTicket
{
    public int StudentTicketId { get; set; }

    public int TicketId { get; set; }

    public decimal? Discount { get; set; }

    public virtual Ticket Ticket { get; set; } = null!;
}