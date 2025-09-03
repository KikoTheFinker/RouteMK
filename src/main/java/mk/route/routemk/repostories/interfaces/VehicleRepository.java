package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.models.Vehicle;

import java.util.List;

public interface VehicleRepository extends GenericRepository<Vehicle, Integer> {
    List<Vehicle> findByTranOrg(TransportOrganizer tranOrg);
}