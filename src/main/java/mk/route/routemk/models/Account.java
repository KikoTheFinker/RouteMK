package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;
import mk.route.routemk.utils.RoleResolver;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Entity
@Table(name = "account")
public class Account implements UserDetails {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer account_id;

    @Column(name = "email")
    protected String email;

    @Column(name = "name")
    protected String name;

    @Column(name = "surname")
    protected String surname;

    @Column(name = "password")
    protected String password;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Admin admin;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Student student;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Driver driver;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private TransportOrganizer transportOrganizer;

    public Account(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public Account() {
    }

    public Admin getAdmin() {
        return admin;
    }

    public Student getStudent() {
        return student;
    }

    public Driver getDriver() {
        return driver;
    }

    public TransportOrganizer getTransportOrganizer() {
        return transportOrganizer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return RoleResolver.resolveRoles(this);
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
