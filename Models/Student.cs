namespace RouteMK.Models;

public class Student
{
    public int StudentId { get; set; }

    public int AccountId { get; set; }

    public string University { get; set; } = null!;

    public string IndexNumber { get; set; } = null!;

    public virtual Account Account { get; set; } = null!;
}