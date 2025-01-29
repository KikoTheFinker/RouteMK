package mk.route.routemk.utils;

import mk.route.routemk.models.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RoleResolver {
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
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return authorities;
    }
}
