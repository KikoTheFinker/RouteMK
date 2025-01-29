package mk.route.routemk.services;

import mk.route.routemk.models.Account;
import mk.route.routemk.repostories.interfaces.AccountRepository;
import mk.route.routemk.services.interfaces.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends GenericServiceImpl<Account, Integer> implements AccountService {

    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
        super(accountRepository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Account save(String email, String name, String surname, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new BadCredentialsException("Passwords don't match up.");
        }

        try {
            return genericRepository.save(new Account(email, name, surname, passwordEncoder.encode(password)));
        } catch (DataIntegrityViolationException existsExc) {
            throw new BadCredentialsException(String.format("Account with email %s already exists.", email));
        }
    }
}