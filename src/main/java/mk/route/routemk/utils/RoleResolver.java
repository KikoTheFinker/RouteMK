package mk.route.routemk.utils;

import mk.route.routemk.models.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleResolver {
    /**
     * Utility function that helps in determining the role of a user through its referencing children.
     * @param account The authorized (user) - account on the current thread.
     * @return Collection of the authorities (roles) belonging to the authorized user.
     */
    public static Collection<? extends GrantedAuthority> resolveRoles(Account account) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (account.getAdmin() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (account.getStudent() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_STUDENT"));
        }
        if (account.getDriver() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_DRIVER"));
        }
        if (account.getTransportOrganizer() != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_TRANSPORT_ORGANIZER"));
        }
        if (account.getTransportOrganizer() == null) { // add the user role for everyone except for transport organizers so they can travel
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return authorities;
    }
}
