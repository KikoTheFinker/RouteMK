package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer adminId;

    @OneToOne(mappedBy = "admin")
    private Account account;

    public Admin(Account account) {
        this.account = account;
    }

    public Admin() {

    }
}
