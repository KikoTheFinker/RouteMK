package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.route.routemk.models.enums.Status;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@Table(name = "trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name = "transport_organizer_id", referencedColumnName = "transport_organizer_id")
    private TransportOrganizer tranOrg;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id")
    private Route route;

    @Column(name = "free_seats")
    private int freeSeats;

    @Column(name = "date")
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name = "trip_stops",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "location_id")
    )
    private Collection<TripStop> stops;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    public int getTripId() {
        return tripId;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }

    public Route getRoute() {
        return route;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public Status getStatus() {
        return status;
    }

    public Collection<TripStop> getStops() {
        return stops;
    }

    public Trip(TransportOrganizer tranOrg,
                Collection<TripStop> stops,
                Route route,
                int freeSeats,
                LocalDate date,
                Status status) {
        this.tranOrg = tranOrg;
        this.route = route;
        this.freeSeats = freeSeats;
        this.date = date;
        this.stops = stops;
        this.status = status;
    }
    public Trip() {
    }
}
