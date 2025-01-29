package mk.route.routemk.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Integer studentId;

    @Column(name = "university")
    private String university;

    @Column(name = "index_number")
    private String indexNumber;

    @OneToOne(mappedBy = "student")
    private Account account;

    // todo: maybe need a list of drivers

    public Student(String university, String indexNumber, Account account) {
        this.university = university;
        this.indexNumber = indexNumber;
        this.account = account;
    }

    public Student() {
    }
}
