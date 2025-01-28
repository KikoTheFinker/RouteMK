package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="account")
public class Account {

    @Id
    @Column(name="account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer account_id;

    @OneToOne(mappedBy = "account")
    private TransportOrganizer transportOrganizer;

    @Column(name="email")
    protected String email;

    @Column(name="name")
    protected String name;

    @Column(name="surname")
    protected String surname;

    @Column(name="password")
    protected String password;

    public Account(String email, String name, String surname, String password) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }

    public Account() {
    }
}
