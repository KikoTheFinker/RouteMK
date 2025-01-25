using Microsoft.EntityFrameworkCore;
using RouteMK.Models;
using Route = RouteMK.Models.Route;

namespace RouteMK.Data.DbContext;

public partial class AppDbContext : Microsoft.EntityFrameworkCore.DbContext
{
    public AppDbContext()
    {
    }

    public AppDbContext(DbContextOptions<AppDbContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Account> Accounts { get; set; }

    public virtual DbSet<Admin> Admins { get; set; }

    public virtual DbSet<Automobile> Automobiles { get; set; }

    public virtual DbSet<Bus> Buses { get; set; }

    public virtual DbSet<ChildTicket> ChildTickets { get; set; }

    public virtual DbSet<Driver> Drivers { get; set; }

    public virtual DbSet<DriverDrivesOnTrip> DriverDrivesOnTrips { get; set; }

    public virtual DbSet<DriverVehicleOperation> DriverVehicleOperations { get; set; }

    public virtual DbSet<Favorite> Favorites { get; set; }

    public virtual DbSet<Location> Locations { get; set; }

    public virtual DbSet<Payment> Payments { get; set; }

    public virtual DbSet<Review> Reviews { get; set; }

    public virtual DbSet<Route> Routes { get; set; }

    public virtual DbSet<Student> Students { get; set; }

    public virtual DbSet<StudentTicket> StudentTickets { get; set; }

    public virtual DbSet<Ticket> Tickets { get; set; }

    public virtual DbSet<TicketRelation> TicketRelations { get; set; }

    public virtual DbSet<Train> Trains { get; set; }

    public virtual DbSet<TransportOrganizer> TransportOrganizers { get; set; }

    public virtual DbSet<Trip> Trips { get; set; }

    public virtual DbSet<TripDaysActive> TripDaysActives { get; set; }

    public virtual DbSet<TripStop> TripStops { get; set; }

    public virtual DbSet<Van> Vans { get; set; }

    public virtual DbSet<Vehicle> Vehicles { get; set; }
    
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder
            .HasPostgresEnum("routemk", "day_of_week", new[] { "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY" })
            .HasPostgresEnum("routemk", "trip_status", new[] { "NOT_STARTED", "IN_PROGRESS", "FINISHED" });

        modelBuilder.Entity<Account>(entity =>
        {
            entity.HasKey(e => e.AccountId).HasName("account_pkey");

            entity.ToTable("account", "routemk");

            entity.HasIndex(e => e.Email, "account_email_key").IsUnique();

            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.Email)
                .HasMaxLength(100)
                .HasColumnName("email");
            entity.Property(e => e.Name)
                .HasMaxLength(50)
                .HasColumnName("name");
            entity.Property(e => e.Password)
                .HasMaxLength(60)
                .HasColumnName("password");
            entity.Property(e => e.Surname)
                .HasMaxLength(50)
                .HasColumnName("surname");
        });

        modelBuilder.Entity<Admin>(entity =>
        {
            entity.HasKey(e => e.AdminId).HasName("admin_pkey");

            entity.ToTable("admin", "routemk");

            entity.Property(e => e.AdminId).HasColumnName("admin_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");

            entity.HasOne(d => d.Account).WithMany(p => p.Admins)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("fk_admin_account_id");
        });

        modelBuilder.Entity<Automobile>(entity =>
        {
            entity.HasKey(e => e.AutomobileId).HasName("automobile_pkey");

            entity.ToTable("automobile", "routemk");

            entity.HasIndex(e => e.VehicleId, "automobile_vehicle_id_key").IsUnique();

            entity.Property(e => e.AutomobileId).HasColumnName("automobile_id");
            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");

            entity.HasOne(d => d.Vehicle).WithOne(p => p.Automobile)
                .HasForeignKey<Automobile>(d => d.VehicleId)
                .HasConstraintName("automobile_vehicle_id_fkey");
        });

        modelBuilder.Entity<Bus>(entity =>
        {
            entity.HasKey(e => e.BusId).HasName("bus_pkey");

            entity.ToTable("bus", "routemk");

            entity.HasIndex(e => e.VehicleId, "bus_vehicle_id_key").IsUnique();

            entity.Property(e => e.BusId).HasColumnName("bus_id");
            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");

            entity.HasOne(d => d.Vehicle).WithOne(p => p.Bus)
                .HasForeignKey<Bus>(d => d.VehicleId)
                .HasConstraintName("bus_vehicle_id_fkey");
        });

        modelBuilder.Entity<ChildTicket>(entity =>
        {
            entity.HasKey(e => e.ChildTicketId).HasName("child_ticket_pkey");

            entity.ToTable("child_ticket", "routemk");

            entity.Property(e => e.ChildTicketId).HasColumnName("child_ticket_id");
            entity.Property(e => e.Discount)
                .HasPrecision(5, 2)
                .HasColumnName("discount");
            entity.Property(e => e.Embg)
                .HasMaxLength(13)
                .HasColumnName("embg");
            entity.Property(e => e.ParentEmbg)
                .HasMaxLength(13)
                .HasColumnName("parent_embg");
            entity.Property(e => e.TicketId).HasColumnName("ticket_id");

            entity.HasOne(d => d.Ticket).WithMany(p => p.ChildTickets)
                .HasForeignKey(d => d.TicketId)
                .HasConstraintName("child_ticket_ticket_id_fkey");
        });

        modelBuilder.Entity<Driver>(entity =>
        {
            entity.HasKey(e => e.DriverId).HasName("driver_pkey");

            entity.ToTable("driver", "routemk");

            entity.Property(e => e.DriverId).HasColumnName("driver_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.TransportOrganizerId).HasColumnName("transport_organizer_id");
            entity.Property(e => e.YearsExperience).HasColumnName("years_experience");

            entity.HasOne(d => d.Account).WithMany(p => p.Drivers)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("driver_account_id_fkey");

            entity.HasOne(d => d.TransportOrganizer).WithMany(p => p.Drivers)
                .HasForeignKey(d => d.TransportOrganizerId)
                .OnDelete(DeleteBehavior.Cascade)
                .HasConstraintName("driver_transport_organizer_id_fkey");
        });

        modelBuilder.Entity<DriverDrivesOnTrip>(entity =>
        {
            entity.HasKey(e => e.DriverDrivesOnTripId).HasName("driver_drives_on_trip_pkey");

            entity.ToTable("driver_drives_on_trip", "routemk");

            entity.Property(e => e.DriverDrivesOnTripId).HasColumnName("driver_drives_on_trip_id");
            entity.Property(e => e.DriverId).HasColumnName("driver_id");
            entity.Property(e => e.TripId).HasColumnName("trip_id");

            entity.HasOne(d => d.Driver).WithMany(p => p.DriverDrivesOnTrips)
                .HasForeignKey(d => d.DriverId)
                .HasConstraintName("driver_drives_on_trip_driver_id_fkey");

            entity.HasOne(d => d.Trip).WithMany(p => p.DriverDrivesOnTrips)
                .HasForeignKey(d => d.TripId)
                .HasConstraintName("driver_drives_on_trip_trip_id_fkey");
        });

        modelBuilder.Entity<DriverVehicleOperation>(entity =>
        {
            entity.HasKey(e => e.DriverVehicleOperationId).HasName("driver_vehicle_operation_pkey");

            entity.ToTable("driver_vehicle_operation", "routemk");

            entity.Property(e => e.DriverVehicleOperationId).HasColumnName("driver_vehicle_operation_id");
            entity.Property(e => e.DriverId).HasColumnName("driver_id");
            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");

            entity.HasOne(d => d.Driver).WithMany(p => p.DriverVehicleOperations)
                .HasForeignKey(d => d.DriverId)
                .HasConstraintName("driver_vehicle_operation_driver_id_fkey");

            entity.HasOne(d => d.Vehicle).WithMany(p => p.DriverVehicleOperations)
                .HasForeignKey(d => d.VehicleId)
                .HasConstraintName("driver_vehicle_operation_vehicle_id_fkey");
        });

        modelBuilder.Entity<Favorite>(entity =>
        {
            entity.HasKey(e => e.FavoriteId).HasName("favorite_pkey");

            entity.ToTable("favorite", "routemk");

            entity.Property(e => e.FavoriteId).HasColumnName("favorite_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.RouteId).HasColumnName("route_id");

            entity.HasOne(d => d.Account).WithMany(p => p.Favorites)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("favorite_account_id_fkey");

            entity.HasOne(d => d.Route).WithMany(p => p.Favorites)
                .HasForeignKey(d => d.RouteId)
                .HasConstraintName("favorite_route_id_fkey");
        });

        modelBuilder.Entity<Location>(entity =>
        {
            entity.HasKey(e => e.LocationId).HasName("location_pkey");

            entity.ToTable("location", "routemk");

            entity.Property(e => e.LocationId).HasColumnName("location_id");
            entity.Property(e => e.Latitude)
                .HasPrecision(9, 6)
                .HasColumnName("latitude");
            entity.Property(e => e.Longitude)
                .HasPrecision(9, 6)
                .HasColumnName("longitude");
            entity.Property(e => e.Name)
                .HasMaxLength(100)
                .HasColumnName("name");
        });

        modelBuilder.Entity<Payment>(entity =>
        {
            entity.HasKey(e => e.PaymentId).HasName("payment_pkey");

            entity.ToTable("payment", "routemk");

            entity.Property(e => e.PaymentId).HasColumnName("payment_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.Date).HasColumnName("date");
            entity.Property(e => e.NTickets).HasColumnName("n_tickets");
            entity.Property(e => e.TotalPrice)
                .HasPrecision(10, 2)
                .HasColumnName("total_price");

            entity.HasOne(d => d.Account).WithMany(p => p.Payments)
                .HasForeignKey(d => d.AccountId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("payment_account_id_fkey");
        });

        modelBuilder.Entity<Review>(entity =>
        {
            entity.HasKey(e => e.ReviewId).HasName("review_pkey");

            entity.ToTable("review", "routemk");

            entity.Property(e => e.ReviewId).HasColumnName("review_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.Description).HasColumnName("description");
            entity.Property(e => e.Rating).HasColumnName("rating");
            entity.Property(e => e.TicketId).HasColumnName("ticket_id");

            entity.HasOne(d => d.Account).WithMany(p => p.Reviews)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("review_account_id_fkey");

            entity.HasOne(d => d.Ticket).WithMany(p => p.Reviews)
                .HasForeignKey(d => d.TicketId)
                .HasConstraintName("review_ticket_id_fkey");
        });

        modelBuilder.Entity<Route>(entity =>
        {
            entity.HasKey(e => e.RouteId).HasName("route_pkey");

            entity.ToTable("route", "routemk");

            entity.Property(e => e.RouteId).HasColumnName("route_id");
            entity.Property(e => e.FromLocationId).HasColumnName("from_location_id");
            entity.Property(e => e.ToLocationId).HasColumnName("to_location_id");
            entity.Property(e => e.TransportOrganizerId).HasColumnName("transport_organizer_id");

            entity.HasOne(d => d.FromLocation).WithMany(p => p.RouteFromLocations)
                .HasForeignKey(d => d.FromLocationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("route_from_location_id_fkey");

            entity.HasOne(d => d.ToLocation).WithMany(p => p.RouteToLocations)
                .HasForeignKey(d => d.ToLocationId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("route_to_location_id_fkey");

            entity.HasOne(d => d.TransportOrganizer).WithMany(p => p.Routes)
                .HasForeignKey(d => d.TransportOrganizerId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("route_transport_organizer_id_fkey");
        });

        modelBuilder.Entity<Student>(entity =>
        {
            entity.HasKey(e => e.StudentId).HasName("student_pkey");

            entity.ToTable("student", "routemk");

            entity.Property(e => e.StudentId).HasColumnName("student_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.IndexNumber)
                .HasMaxLength(20)
                .HasColumnName("index_number");
            entity.Property(e => e.University)
                .HasMaxLength(100)
                .HasColumnName("university");

            entity.HasOne(d => d.Account).WithMany(p => p.Students)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("student_account_id_fkey");
        });

        modelBuilder.Entity<StudentTicket>(entity =>
        {
            entity.HasKey(e => e.StudentTicketId).HasName("student_ticket_pkey");

            entity.ToTable("student_ticket", "routemk");

            entity.Property(e => e.StudentTicketId).HasColumnName("student_ticket_id");
            entity.Property(e => e.Discount)
                .HasPrecision(5, 2)
                .HasColumnName("discount");
            entity.Property(e => e.TicketId).HasColumnName("ticket_id");

            entity.HasOne(d => d.Ticket).WithMany(p => p.StudentTickets)
                .HasForeignKey(d => d.TicketId)
                .HasConstraintName("student_ticket_ticket_id_fkey");
        });

        modelBuilder.Entity<Ticket>(entity =>
        {
            entity.HasKey(e => e.TicketId).HasName("ticket_pkey");

            entity.ToTable("ticket", "routemk");

            entity.Property(e => e.TicketId).HasColumnName("ticket_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.DatePurchased).HasColumnName("date_purchased");
            entity.Property(e => e.GetsOffLocationId).HasColumnName("gets_off_location_id");
            entity.Property(e => e.GetsOnLocationId).HasColumnName("gets_on_location_id");
            entity.Property(e => e.PaymentId).HasColumnName("payment_id");
            entity.Property(e => e.Price)
                .HasPrecision(10, 2)
                .HasColumnName("price");
            entity.Property(e => e.Seat)
                .HasMaxLength(10)
                .HasColumnName("seat");
            entity.Property(e => e.TimePurchased).HasColumnName("time_purchased");
            entity.Property(e => e.TripId).HasColumnName("trip_id");

            entity.HasOne(d => d.Account).WithMany(p => p.Tickets)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("ticket_account_id_fkey");

            entity.HasOne(d => d.GetsOffLocation).WithMany(p => p.TicketGetsOffLocations)
                .HasForeignKey(d => d.GetsOffLocationId)
                .HasConstraintName("gets_off_location_fkey");

            entity.HasOne(d => d.GetsOnLocation).WithMany(p => p.TicketGetsOnLocations)
                .HasForeignKey(d => d.GetsOnLocationId)
                .HasConstraintName("gets_on_location_fkey");

            entity.HasOne(d => d.Payment).WithMany(p => p.Tickets)
                .HasForeignKey(d => d.PaymentId)
                .HasConstraintName("ticket_payment_id_fkey");
        });

        modelBuilder.Entity<TicketRelation>(entity =>
        {
            entity.HasKey(e => e.TicketRelationId).HasName("ticket_relations_pkey");

            entity.ToTable("ticket_relations", "routemk");

            entity.Property(e => e.TicketRelationId).HasColumnName("ticket_relation_id");
            entity.Property(e => e.ChildTicketId).HasColumnName("child_ticket_id");
            entity.Property(e => e.ParentTicketId).HasColumnName("parent_ticket_id");

            entity.HasOne(d => d.ChildTicket).WithMany(p => p.TicketRelationChildTickets)
                .HasForeignKey(d => d.ChildTicketId)
                .HasConstraintName("ticket_relations_child_ticket_id_fkey");

            entity.HasOne(d => d.ParentTicket).WithMany(p => p.TicketRelationParentTickets)
                .HasForeignKey(d => d.ParentTicketId)
                .HasConstraintName("ticket_relations_parent_ticket_id_fkey");
        });

        modelBuilder.Entity<Train>(entity =>
        {
            entity.HasKey(e => e.TrainId).HasName("train_pkey");

            entity.ToTable("train", "routemk");

            entity.HasIndex(e => e.VehicleId, "train_vehicle_id_key").IsUnique();

            entity.Property(e => e.TrainId).HasColumnName("train_id");
            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");

            entity.HasOne(d => d.Vehicle).WithOne(p => p.Train)
                .HasForeignKey<Train>(d => d.VehicleId)
                .HasConstraintName("train_vehicle_id_fkey");
        });

        modelBuilder.Entity<TransportOrganizer>(entity =>
        {
            entity.HasKey(e => e.TransportOrganizerId).HasName("transport_organizer_pkey");

            entity.ToTable("transport_organizer", "routemk");

            entity.Property(e => e.TransportOrganizerId).HasColumnName("transport_organizer_id");
            entity.Property(e => e.AccountId).HasColumnName("account_id");
            entity.Property(e => e.CompanyEmbg)
                .HasMaxLength(50)
                .HasColumnName("company_embg");
            entity.Property(e => e.CompanyName)
                .HasMaxLength(100)
                .HasColumnName("company_name");

            entity.HasOne(d => d.Account).WithMany(p => p.TransportOrganizers)
                .HasForeignKey(d => d.AccountId)
                .HasConstraintName("transport_organizer_account_id_fkey");
        });

        modelBuilder.Entity<Trip>(entity =>
        {
            entity.HasKey(e => e.TripId).HasName("trip_pkey");

            entity.ToTable("trip", "routemk");

            entity.Property(e => e.TripId).HasColumnName("trip_id");
            entity.Property(e => e.Date).HasColumnName("date");
            entity.Property(e => e.FreeSeats).HasColumnName("free_seats");
            entity.Property(e => e.RouteId).HasColumnName("route_id");
            entity.Property(e => e.TransportOrganizerId).HasColumnName("transport_organizer_id");

            entity.HasOne(d => d.Route).WithMany(p => p.Trips)
                .HasForeignKey(d => d.RouteId)
                .HasConstraintName("trip_route_id_fkey");

            entity.HasOne(d => d.TransportOrganizer).WithMany(p => p.Trips)
                .HasForeignKey(d => d.TransportOrganizerId)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("trip_transport_organizer_id_fkey");
        });

        modelBuilder.Entity<TripDaysActive>(entity =>
        {
            entity.HasKey(e => e.TripDaysActiveId).HasName("trip_days_active_pkey");

            entity.ToTable("trip_days_active", "routemk");

            entity.Property(e => e.TripDaysActiveId).HasColumnName("trip_days_active_id");
            entity.Property(e => e.RouteId).HasColumnName("route_id");

            entity.HasOne(d => d.Route).WithMany(p => p.TripDaysActives)
                .HasForeignKey(d => d.RouteId)
                .HasConstraintName("trip_days_active_route_id_fkey");
        });

        modelBuilder.Entity<TripStop>(entity =>
        {
            entity.HasKey(e => e.TripStopId).HasName("trip_stops_pkey");

            entity.ToTable("trip_stops", "routemk");

            entity.Property(e => e.TripStopId).HasColumnName("trip_stop_id");
            entity.Property(e => e.LocationId).HasColumnName("location_id");
            entity.Property(e => e.StopTime).HasColumnName("stop_time");
            entity.Property(e => e.TripId).HasColumnName("trip_id");

            entity.HasOne(d => d.Location).WithMany(p => p.TripStops)
                .HasForeignKey(d => d.LocationId)
                .HasConstraintName("trip_stops_location_id_fkey");

            entity.HasOne(d => d.Trip).WithMany(p => p.TripStops)
                .HasForeignKey(d => d.TripId)
                .HasConstraintName("trip_stops_trip_id_fkey");
        });

        modelBuilder.Entity<Van>(entity =>
        {
            entity.HasKey(e => e.VanId).HasName("van_pkey");

            entity.ToTable("van", "routemk");

            entity.HasIndex(e => e.VehicleId, "van_vehicle_id_key").IsUnique();

            entity.Property(e => e.VanId).HasColumnName("van_id");
            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");

            entity.HasOne(d => d.Vehicle).WithOne(p => p.Van)
                .HasForeignKey<Van>(d => d.VehicleId)
                .HasConstraintName("van_vehicle_id_fkey");
        });

        modelBuilder.Entity<Vehicle>(entity =>
        {
            entity.HasKey(e => e.VehicleId).HasName("vehicle_pkey");

            entity.ToTable("vehicle", "routemk");

            entity.Property(e => e.VehicleId).HasColumnName("vehicle_id");
            entity.Property(e => e.Brand)
                .HasMaxLength(30)
                .HasColumnName("brand");
            entity.Property(e => e.Capacity)
                .HasMaxLength(20)
                .HasColumnName("capacity");
            entity.Property(e => e.Model)
                .HasMaxLength(30)
                .HasColumnName("model");
            entity.Property(e => e.YearManufactured)
                .HasMaxLength(10)
                .HasColumnName("year_manufactured");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
