package discordstudy.calender.domain.member.entity;

import discordstudy.calender.domain.team.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "loginId",nullable = false,unique = true)
    private String loginId;

    @Column(name = "nickname",nullable = false)
    private String nickname;

    @Column(name = "password",nullable = true)
    private String password;

    @Enumerated(EnumType.STRING)//Role 문자열로 저장
    @Column(name="role",nullable = false)
    private Role role;
    public Member(String loginId, String nickname, String password,Role role) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.role=role;
    }
}
