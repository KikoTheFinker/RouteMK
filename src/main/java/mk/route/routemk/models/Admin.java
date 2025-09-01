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

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private Account account;

    public Integer getAdminId() {
        return adminId;
    }


    @Override
    public String toString() {
        return String.format("Administrator: %s %s", account.getName(), account.getSurname());
    }

    public Admin() {

    }
}
