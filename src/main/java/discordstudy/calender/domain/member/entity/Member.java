package discordstudy.calender.domain.member.entity;

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

    @Column(name = "loginId",nullable = false)
    private String loginId;

    @Column(name = "nickname",nullable = false)
    private String nickname;

    @Column(name = "password",nullable = true)
    private String password;
}
