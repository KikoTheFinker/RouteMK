package mk.route.routemk.services;

import mk.route.routemk.models.Account;
import mk.route.routemk.repostories.interfaces.AccountRepository;
import mk.route.routemk.services.interfaces.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {

    public AccountServiceImpl(AccountRepository accountRepository) {
        super(accountRepository);
    }
}