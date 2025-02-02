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
    @JoinColumn(name = "trip_id", referencedColumnName = "trip_id", nullable = false, insertable = false, updatable = false)
    private Trip trip;

    @Column(name = "trip_id")
    private Integer tripId;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false, insertable = false, updatable = false)
    private Location location;

    @Column(name = "location_id")
    private Integer locationId;

    @Column(name = "stop_time", nullable = false)
    private LocalTime stopTime;

    public TripStop(Trip trip, Location location, LocalTime stopTime) {
        this.trip = trip;
        this.location = location;
        this.stopTime = stopTime;
    }

    public TripStop() {
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStopTime(LocalTime stopTime) {
        this.stopTime = stopTime;
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