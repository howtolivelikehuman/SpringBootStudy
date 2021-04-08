package hello.jpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(name = "MEMBER_SEQ_GENERATOR", sequenceName = "MEMBER_SEQ")
//@TableGenerator(name = "MEMBER_SEQ_GENERATOR",table = "MY_SEQUENCES",pkColumnValue = “MEMBER_SEQ", allocationSize = 1)
public class User {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    //@GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User() {
    }
}
