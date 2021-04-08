package hello.jpa;

import org.h2.engine.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Member {
    @Id
    private Long id;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.username = name;
    }

    @Column(name = "name")
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;


    @Transient
    private int notDB;

    //Getter, Setter…
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public void setRoleType(RoleType A){
        this.roleType = A;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }
}

