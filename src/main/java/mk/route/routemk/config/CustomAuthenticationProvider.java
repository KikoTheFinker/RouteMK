package mk.route.routemk.config;

import mk.route.routemk.models.Account;
import mk.route.routemk.services.interfaces.GenericService;
import mk.route.routemk.specifications.AccountSpecifications;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final GenericService<Account, Integer> accountService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(GenericService<Account, Integer> accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (!username.isEmpty() || password.isEmpty()) {
            throw new BadCredentialsException("Invalid username or password");
        }
        UserDetails userDetails = accountService.findOneByPredicate(AccountSpecifications.hasEmail(username));

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
