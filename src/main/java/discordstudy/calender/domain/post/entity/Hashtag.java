package discordstudy.calender.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Entity
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", length = 20, unique = true)
    private String tag;

    @OneToMany(
            mappedBy = "hashtag",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<HashtagMap> hashtagMaps = new HashSet<>();

    public Hashtag(String tag) {
        this.tag = tag;
    }
}