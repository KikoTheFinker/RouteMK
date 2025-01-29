package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Account;

public interface AccountService extends GenericService<Account, Integer> {
    Account findOneByPredicate(String email);
}
