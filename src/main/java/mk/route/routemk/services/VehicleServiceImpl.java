package mk.route.routemk.services;

import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.models.Vehicle;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.VehicleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleServiceImpl extends GenericServiceImpl<Vehicle, Integer> implements VehicleService {

    public VehicleServiceImpl(GenericRepository<Vehicle, Integer> vehicleRepository) {
        super(vehicleRepository);
    }

    @Override
    public List<Vehicle> findByTranOrg(TransportOrganizer tranOrg) {
        return ((mk.route.routemk.repostories.interfaces.VehicleRepository) genericRepository)
                .findByTranOrg(tranOrg);
    }
}
