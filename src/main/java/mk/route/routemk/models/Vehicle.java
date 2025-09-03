package mk.route.routemk.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Integer vehicleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transport_organizer_id", referencedColumnName = "transport_organizer_id", insertable = false, updatable = false)
    private TransportOrganizer tranOrg;

    @Column(name = "transport_organizer_id", nullable = false)
    private Integer tranOrgId;

    @Column(name = "model", nullable = false, length = 30)
    private String model;

    @Column(name = "brand", nullable = false, length = 30)
    private String brand;

    @Column(name = "capacity", nullable = false, length = 20)
    private String capacity;

    @Column(name = "year_manufactured", length = 10)
    private String yearManufactured;

    public Vehicle() {
    }

    public Vehicle(Integer tranOrgId, String model, String brand, String capacity, String yearManufactured) {
        this.tranOrgId = tranOrgId;
        this.model = model;
        this.brand = brand;
        this.capacity = capacity;
        this.yearManufactured = yearManufactured;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public TransportOrganizer getTranOrg() {
        return tranOrg;
    }

    public void setTranOrg(TransportOrganizer tranOrg) {
        this.tranOrg = tranOrg;
    }

    public Integer getTranOrgId() {
        return tranOrgId;
    }

    public void setTranOrgId(Integer tranOrgId) {
        this.tranOrgId = tranOrgId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getYearManufactured() {
        return yearManufactured;
    }

    public void setYearManufactured(String yearManufactured) {
        this.yearManufactured = yearManufactured;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%s)", brand, model, yearManufactured);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle vehicle)) return false;
        return Objects.equals(vehicleId, vehicle.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleId);
    }
}
