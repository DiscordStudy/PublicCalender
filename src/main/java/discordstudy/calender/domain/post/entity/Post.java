package discordstudy.calender.domain.post.entity;

import discordstudy.calender.domain.member.entity.Member;
import discordstudy.calender.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column //카테고리 분류를 먼저 정해야 하므로 일단 보류
//    @Enumerated(EnumType.STRING)
//    private Category category;

    @Setter
    @Column(name = "title", length = 20)
    private String title;

    @Setter
    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<HashtagMap> hashtagMaps = new HashSet<>();
}
