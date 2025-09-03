package mk.route.routemk.services;

import mk.route.routemk.models.DriverVehicleOperation;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.DriverVehicleOperationService;
import org.springframework.stereotype.Service;

@Service
public class DriverVehicleOperationServiceImpl
        extends GenericServiceImpl<DriverVehicleOperation, Integer>
        implements DriverVehicleOperationService {

    public DriverVehicleOperationServiceImpl(GenericRepository<DriverVehicleOperation, Integer> repo) {
        super(repo);
    }
}
