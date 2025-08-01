package mk.route.routemk.services.auth;

import mk.route.routemk.models.Account;
import mk.route.routemk.models.Driver;
import mk.route.routemk.models.TransportOrganizer;
import mk.route.routemk.services.auth.interfaces.AuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * Retrieves the current authentication object if authenticated.
     *
     * @return Optional containing Authentication object, else empty.
     */
    private Optional<Authentication> getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(Authentication::isAuthenticated);
    }

    /**
     * Retrieves the authenticated Account object if present.
     *
     * @return Optional containing Account, else empty.
     */
    public Optional<Account> getAuthenticatedAccount() {
        return getAuthentication()
                .map(Authentication::getPrincipal)
                .filter(Account.class::isInstance)
                .map(Account.class::cast);
    }

    /**
     * Retrieves the authenticated user's ID.
     *
     * @return User ID if authenticated, else null.
     */
    public Integer getAuthenticatedUserId() {
        return getAuthenticatedAccount()
                .map(Account::getAccountId)
                .orElse(null);
    }

    /**
     * Retrieves the Transport Organizer's ID.
     *
     * @return Transport Organizer ID if authenticated, else null.
     */
    public Integer getTransportOrganizerId() {
        return getAuthenticatedAccount()
                .map(Account::getTransportOrganizer)
                .map(TransportOrganizer::getTranOrgId)
                .orElse(null);
    }

    public TransportOrganizer getTransportOrganizer() {
        return getAuthenticatedAccount()
                .map(Account::getTransportOrganizer)
                .orElse(null);
    }

    /**
     * Retrieves the Transport Organizer ID from the authenticated user's Driver account.
     *
     * @return Transport Organizer ID if user is a Driver, else null.
     */
    public Integer getTransportOrganizerIdFromDriverAccountId() {
        return getAuthenticatedAccount()
                .map(Account::getDriver)
                .map(Driver::getTranOrg)
                .map(TransportOrganizer::getTranOrgId)
                .orElse(null);
    }


    /**
     * Retrieves the roles assigned to the authenticated user.
     *
     * @return Set of role names (e.g., ROLE_ADMIN, ROLE_USER).
     */
    public Set<String> getRoles() {
        return getAuthentication()
                .map(auth -> auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toSet()))
                .orElse(Set.of());
    }

    /**
     * Checks if the authenticated user has a specific role.
     *
     * @param role The role to check upon (ROLE_ADMIN, ROLE_USER, etc.)
     * @return true if authenticated user has the role, else false.
     */
    public boolean hasRole(String role) {
        return getRoles().contains("ROLE_" + role);
    }

}
