package mk.route.routemk.services;

import mk.route.routemk.models.Account;
import mk.route.routemk.repostories.interfaces.GenericRepository;
import mk.route.routemk.services.interfaces.AccountService;
import mk.route.routemk.specifications.AccountSpecifications;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {

    public AccountServiceImpl(GenericRepository<Account, Integer> genericRepository, Class<Account> entityType) {
        super(genericRepository, entityType);
    }

    @Override
    public Account findOneByPredicate(String email) {
        return findOneByPredicate(AccountSpecifications.hasEmail(email));
    }
}
