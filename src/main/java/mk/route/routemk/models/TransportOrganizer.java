package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transport_organizer")
public class TransportOrganizer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transport_organizer_id")
    private Integer tranOrgId;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_embg")
    private String companyEmbg;

    @OneToOne
    @JoinColumn(name="account_id", referencedColumnName = "account_id")
    private Account account;

    // todo: maybe need a list of drivers

    public TransportOrganizer(String companyName, String companyEmbg, Account account) {
        this.companyName = companyName;
        this.companyEmbg = companyEmbg;
        this.account = account;
    }

    public TransportOrganizer() {
    }

    public Integer getTranOrgId() {
        return tranOrgId;
    }

    public String getCompanyName() {
        return companyName;
    }
}
