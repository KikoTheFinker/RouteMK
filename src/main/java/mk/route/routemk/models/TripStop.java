package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "trip_stops")
public class TripStop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_stop_id")
    private Integer tripStopId;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false)
    private Location location;

    @Column(name = "stop_time", nullable = false)
    private LocalTime stopTime;

    public TripStop(Trip trip, Location location, LocalTime stopTime) {
        this.trip = trip;
        this.location = location;
        this.stopTime = stopTime;
    }

    public TripStop() {
    }

    public Integer getTripStopId() {
        return tripStopId;
    }

    public Trip getTrip() {
        return trip;
    }

    public Location getLocation() {
        return location;
    }

    public LocalTime getStopTime() {
        return stopTime;
    }
}