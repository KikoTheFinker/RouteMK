package mk.route.routemk.config;

import mk.route.routemk.exceptions.EntityNotFoundException;
import mk.route.routemk.services.interfaces.AccountService;
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
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user based on provided credentials.
     *
     * @param authentication The authentication request containing username and password.
     * @return A fully authenticated token if authentication is successful.
     * @throws AuthenticationException If authentication fails due to invalid credentials.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (username.isEmpty() || password.isEmpty()) {
            throw new BadCredentialsException("Please fill out all fields.");
        }

        try {
            UserDetails userDetails = accountService.findOneByPredicate(AccountSpecifications.hasEmail(username));

            //Password must be BCRYPT encoded in DATABASE!!
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } catch (EntityNotFoundException exc) {
            throw new BadCredentialsException(exc.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
