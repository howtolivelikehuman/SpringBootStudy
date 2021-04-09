package hello.jpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;


    //관례로 초기화
    @OneToMany(mappedBy = "team")
    private List<Member> members = new ArrayList<>();


    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        member.setTeam(this);
        this.members.add(member);
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}