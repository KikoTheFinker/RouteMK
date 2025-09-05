package mk.route.routemk.models;

import jakarta.persistence.*;

@Entity
@Table(name = "driver_vehicle_operation")
public class DriverVehicleOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_vehicle_operation_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "driver_id", insertable = false, updatable = false)
    private Driver driver;

    @Column(name = "driver_id", nullable = false)
    private Integer driverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", referencedColumnName = "vehicle_id", insertable = false, updatable = false)
    private Vehicle vehicle;

    @Column(name = "vehicle_id", nullable = false)
    private Integer vehicleId;

    public DriverVehicleOperation() {
    }

    public DriverVehicleOperation(Integer driverId, Integer vehicleId) {
        this.driverId = driverId;
        this.vehicleId = vehicleId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }
}
