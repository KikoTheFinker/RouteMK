package mk.route.routemk.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "favorite")
public class Favorite implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favorite_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false, foreignKey = @ForeignKey(name = "favorite_route_id_fkey"))
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false, foreignKey = @ForeignKey(name = "favorite_account_id_fkey"))
    private Account account;

    public Favorite() {}

    public Favorite(Route route, Account account) {
        this.route = route;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
