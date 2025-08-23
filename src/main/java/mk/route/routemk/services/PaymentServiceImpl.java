package mk.route.routemk.services;

import mk.route.routemk.models.Payment;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl extends GenericServiceImpl<Payment, Integer> implements PaymentService {
    public PaymentServiceImpl(GenericRepository<Payment, Integer> genericRepository) {
        super(genericRepository);
    }

    //TODO: implement payment processing

}
