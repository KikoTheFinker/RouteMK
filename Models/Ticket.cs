namespace RouteMK.Models;

public class Ticket
{
    public int TicketId { get; set; }

    public int TripId { get; set; }

    public int GetsOnLocationId { get; set; }

    public int GetsOffLocationId { get; set; }

    public int AccountId { get; set; }

    public DateOnly DatePurchased { get; set; }

    public TimeOnly TimePurchased { get; set; }

    public decimal? Price { get; set; }

    public string? Seat { get; set; }

    public int PaymentId { get; set; }

    public virtual Account Account { get; set; } = null!;

    public virtual ICollection<ChildTicket> ChildTickets { get; set; } = new List<ChildTicket>();

    public virtual Location GetsOffLocation { get; set; } = null!;

    public virtual Location GetsOnLocation { get; set; } = null!;

    public virtual Payment Payment { get; set; } = null!;

    public virtual ICollection<Review> Reviews { get; set; } = new List<Review>();

    public virtual ICollection<StudentTicket> StudentTickets { get; set; } = new List<StudentTicket>();

    public virtual ICollection<TicketRelation> TicketRelationChildTickets { get; set; } = new List<TicketRelation>();

    public virtual ICollection<TicketRelation> TicketRelationParentTickets { get; set; } = new List<TicketRelation>();
}