package mk.route.routemk.repostories.interfaces;

import mk.route.routemk.models.Driver;
import mk.route.routemk.models.TransportOrganizer;

import java.util.List;

public interface DriverRepository extends GenericRepository<Driver, Integer>{
    List<Driver> findByTranOrg(TransportOrganizer tranOrg);
}
