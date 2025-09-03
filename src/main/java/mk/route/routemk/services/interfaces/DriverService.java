package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Driver;
import mk.route.routemk.models.TransportOrganizer;

import java.util.List;

public interface DriverService extends GenericService<Driver, Integer> {
    List<Driver> findByTranOrg(TransportOrganizer tranOrg);
}
