package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Vehicle;
import mk.route.routemk.models.TransportOrganizer;

import java.util.List;

public interface VehicleService extends GenericService<Vehicle, Integer> {
    List<Vehicle> findByTranOrg(TransportOrganizer tranOrg);
}
