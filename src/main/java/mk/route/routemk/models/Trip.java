package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Data
@Entity
@NoArgsConstructor
@Table(name="trip")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="trip_id")
    private int tripId;

    @ManyToOne
    @JoinColumn(name="transport_organizer_id", referencedColumnName = "transport_organizer_id")
    private TransportOrganizer tranOrg;

    @ManyToOne
    @JoinColumn(name="route_id", referencedColumnName = "route_id")
    private Route route;

    @Column(name="free_seats")
    private int freeSeats;

    @Column(name="date")
    private LocalDate date;

    @ManyToMany
    @JoinTable(
            name="trip_stops",
            joinColumns = @JoinColumn(name="trip_id"),
            inverseJoinColumns = @JoinColumn(name="location_id")
    )
    private Collection<Location> stops;

    public Trip(TransportOrganizer tranOrg,
                Collection<Location> stops,
                Route route,
                int freeSeats,
                LocalDate date) {
        this.tranOrg = tranOrg;
        this.route = route;
        this.freeSeats = freeSeats;
        this.date = date;
        this.stops = stops;
    }

    public Trip() {
    }
}
