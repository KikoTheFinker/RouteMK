namespace RouteMK.Models;

public class Payment
{
    public int PaymentId { get; set; }

    public int AccountId { get; set; }

    public DateOnly Date { get; set; }

    public decimal TotalPrice { get; set; }

    public int NTickets { get; set; }

    public virtual Account Account { get; set; } = null!;

    public virtual ICollection<Ticket> Tickets { get; set; } = new List<Ticket>();
}