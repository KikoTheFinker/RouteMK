package mk.route.routemk.services.interfaces;

import mk.route.routemk.models.Account;

public interface AccountService extends GenericService<Account, Integer> {
    Account save(String email, String name, String surname, String password, String confirmPassword);
}
