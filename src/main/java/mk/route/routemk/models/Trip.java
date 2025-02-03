package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;
import mk.route.routemk.models.enums.Status;
import org.hibernate.annotations.Type;

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
    @JoinColumn(name = "transport_organizer_id", referencedColumnName = "transport_organizer_id", insertable = false, updatable = false)
    private TransportOrganizer tranOrg;

    @Column(name = "transport_organizer_id")
    private Integer tranOrgId;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "route_id", insertable = false, updatable = false)
    private Route route;

    @Column(name = "route_id")
    private Integer routeId;

    @Column(name = "free_seats")
    private int freeSeats;

    @Column(name = "date")
    private LocalDate date;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("stopTime ASC")
    private Collection<TripStop> stops;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.NOT_STARTED;

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

    public int getTripId() {
        return tripId;
    }

    public void setTranOrg(TransportOrganizer tranOrg) {
        this.tranOrg = tranOrg;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setFreeSeats(int freeSeats) {
        this.freeSeats = freeSeats;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStops(Collection<TripStop> stops) {
        this.stops = stops;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }

    public void setTranOrgId(Integer tranOrgId) {
        this.tranOrgId = tranOrgId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Route getRoute() {
        return route;
    }

    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }

    public Collection<TripStop> getStops() {
        return stops;
    }

    public int getFreeSeats() {
        return freeSeats;
    }

    public Status getStatus() {
        return status;
    }
}
