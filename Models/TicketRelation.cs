namespace RouteMK.Models;

public class TicketRelation
{
    public int TicketRelationId { get; set; }

    public int ParentTicketId { get; set; }

    public int ChildTicketId { get; set; }

    public virtual Ticket ChildTicket { get; set; } = null!;

    public virtual Ticket ParentTicket { get; set; } = null!;
}